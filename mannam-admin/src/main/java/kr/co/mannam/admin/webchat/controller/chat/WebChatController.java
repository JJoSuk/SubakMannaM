package kr.co.mannam.admin.webchat.controller.chat;

import kr.co.mannam.admin.webchat.dto.chat.WebChatDTO;
import kr.co.mannam.admin.webchat.dto.chat.WebChatRoomMap;
import kr.co.mannam.admin.webchat.service.chat.MsgChatService;
import kr.co.mannam.admin.webchat.service.chat.WebChatRoomService;
import kr.co.mannam.admin.webchat.type.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebChatController {

    private final SimpMessageSendingOperations template;
    private final WebChatRoomService webChatRoomService;
    private final MsgChatService msgChatService;

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    // 채팅 페이지
    @GetMapping("/chat")
    public String chatPage() {
        return "webchat/roomlist";
    }

    /**
     * 채팅방 입장 처리
     * @param chat WebChatDto
     * @param headerAccessor SimpMessageHeaderAccessor
     */
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload WebChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {

        // 채팅방 유저+1
        webChatRoomService.plusUserCnt(chat.getRoomId());

        // 채팅방에 유저 추가 및 UserUUID 반환
        String userUUID = msgChatService.addUser(WebChatRoomMap.getInstance().getChatRooms(), chat.getRoomId(), chat.getSender());

        // 반환 결과를 socket session 에 userUUID 로 저장
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(chat.getSender() + " 님 입장!!");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

    }

    /**
     * 채팅 메시지 전송 처리
     * @param chat WebChatDto
     */
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload WebChatDTO chat) {
        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    /**
     * 유저 퇴장 시에는 EventListener 를 통해서 유저 퇴장 확인
     * @param event 퇴장 메시지 호출
     */
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);

        // 채팅방 유저 -1
        webChatRoomService.minusUserCnt(roomId);

        // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        String username = msgChatService.findUserNameByRoomIdAndUserUUID(WebChatRoomMap.getInstance().getChatRooms(), roomId, userUUID);
        msgChatService.delUser(WebChatRoomMap.getInstance().getChatRooms(), roomId, userUUID);

        if (username != null) {
            log.info("User Disconnected : " + username);

            // builder 어노테이션 활용
            WebChatDTO chat = WebChatDTO.builder()
                    .type(MessageType.EXIT)
                    .sender(username)
                    .message(username + " 님 퇴장!!")
                    .build();

            template.convertAndSend("/sub/chat/room/" + roomId, chat);
        }
    }

    /**
     * 채팅에 참여한 유저 리스트 반환
     * @param roomId 채팅방 아아디
     * @return
     */
    public ArrayList<String> userList(String roomId) {

        return msgChatService.getUserList(WebChatRoomMap.getInstance().getChatRooms(), roomId);
    }

    /**
     * 채팅에 참여한 유저 닉네임 중복 확인
     * @param roomId 채팅방 아이디
     * @param username 유저 닉네임
     * @return
     */
    @GetMapping("/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {

        // 유저 이름 확인
        String userName = msgChatService.isDuplicateName(WebChatRoomMap.getInstance().getChatRooms(), roomId, username);
        log.info("동작확인 {}", userName);

        return userName;
    }
}