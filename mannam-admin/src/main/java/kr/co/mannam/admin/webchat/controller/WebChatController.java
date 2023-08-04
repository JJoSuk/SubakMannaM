package kr.co.mannam.admin.webchat.controller;

import kr.co.mannam.admin.webchat.dto.WebChatDTO;
import kr.co.mannam.admin.webchat.service.WebChatRoomService;
import kr.co.mannam.admin.webchat.type.webchat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebChatController {

    private final SimpMessageSendingOperations template;
    private final WebChatRoomService webChatRoomService;

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    /**
     * 채팅방 입장 처리
     * @param chat WebChatDto
     * @param headerAccessor SimpMessageHeaderAccessor
     */
    @MessageMapping("/put/chat/enterUser")
    public void enterUser(@Payload WebChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {
        String roomId = chat.getRoomId();
        String sender = chat.getSender();

        // 유저가 채팅방에 입장하면 채팅방 인원 +1, 유저 추가
        webChatRoomService.plusUserCnt(roomId);
        String userUUID = webChatRoomService.addUser(roomId, sender);

        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", roomId);

        chat.setMessage(sender + " 님 입장하셨습니다.");
        chat.setType(MessageType.ENTER);

        template.convertAndSend("/sub/chat/room/" + roomId, chat);
    }

    /**
     * 채팅 메시지 전송 처리
     * @param chat WebChatDto
     */
    @MessageMapping("/put/chat/sendMessage")
    public void sendMessage(@Payload WebChatDTO chat) {
        String roomId = chat.getRoomId();
        chat.setType(MessageType.TALK);

        // 메시지 전송
        template.convertAndSend("/sub/chat/room/" + roomId, chat);
    }

    /**
     * 유저 퇴장 시에는 EventListener 를 통해서 유저 퇴장 확인
     * @param event 퇴장 메시지 호출
     */
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        // 채팅방 유저 -1
        webChatRoomService.minusUserCnt(roomId);

        // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        String username = webChatRoomService.getUserName(roomId, userUUID);
        webChatRoomService.delUser(roomId, userUUID);

        if (username != null) {
            log.info("User Disconnected : " + username);

            // builder 어노테이션 활용
            WebChatDTO chat = WebChatDTO.builder()
                    .type(MessageType.EXIT)
                    .sender(username)
                    .message(username + " 님 퇴장하셨습니다.")
                    .build();

            template.convertAndSend("/sub/chat/room/" + roomId, chat);
        }
    }

    /**
     * 채팅에 참여한 유저 리스트 반환
     * @param roomId 채팅방 아아디
     * @return
     */
    @GetMapping("/put/chat/userlist")
    @ResponseBody
    public List<String> userList(@RequestParam String roomId) {
        return webChatRoomService.getUserList(roomId);
    }

    /**
     * 채팅에 참여한 유저 닉네임 중복 확인
     * @param roomId 채팅방 아이디
     * @param username 유저 닉네임
     * @return
     */
    @GetMapping("/put/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam String roomId, @RequestParam String username) {
        return webChatRoomService.isDuplicateName(roomId, username);
    }
}
