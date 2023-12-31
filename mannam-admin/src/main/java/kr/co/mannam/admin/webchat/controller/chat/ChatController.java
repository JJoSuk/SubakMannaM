package kr.co.mannam.admin.webchat.controller.chat;

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
import kr.co.mannam.admin.webchat.dto.chat.ChatDto;
import kr.co.mannam.admin.webchat.service.chat.ChatService;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    // 사용자가 채팅방에 입장할 때 호출되는 메서드. 이 메서드는 웹소켓을 통해 /chat/enterUser 경로로 요청이 들어오면 실행된다
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {

        // 채팅방에 유저 추가 및 UserUUID 반환
        String userUUID = chatService.addUser(chat.getRoomId(), chat.getSender());

        // 반환 결과를 socket session 에 userUUID 로 저장
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        // 사용자가 입장 했을 때 사용자 이름 채팅방에 출력
        chat.setMessage(chat.getSender() + " 님 입장!!");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

        chatService.insertchat(chat);

        // 채팅방 유저+1
        chatService.plusUserCnt(chat.getRoomId());
    }

    // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {

        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);

        // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        String username = chatService.getUserName(roomId, userUUID);
        chatService.delUser(roomId, userUUID);

        System.out.println("username = " + username);

        if (username != null) {
            log.info("User Disconnected : " + username);

            // builder 어노테이션 활용
            ChatDto chat = ChatDto.builder()
                    .type(ChatDto.MessageType.LEAVE)
                    .sender(username)
                    .roomId(roomId)
                    .message(username + " 님 퇴장!!")
                    .build();

            template.convertAndSend("/sub/chat/room/" + roomId, chat);

            chatService.insertchat(chat);
        }

        // 채팅방 유저 -1
        chatService.minusUserCnt(roomId);
    }

    // 사용자가 메시지를 보낼 때 호출되는 메서드.
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat) {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());

        System.out.println("chat.getRoomId() = " + chat.getRoomId());
        System.out.println("chat.getMessage() = " + chat.getMessage());
        System.out.println("chat.getSender() = " + chat.getSender());

        chatService.insertchat(chat);

        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }


    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/chat/userlist")
    @ResponseBody
    public ArrayList<String> userList(String roomId) {
        return chatService.getUserList(roomId);
    }

    // 채팅에 참여한 유저 닉네임 중복 확인
    @GetMapping("/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {

        // 유저 이름 확인
        String userName = chatService.isDuplicateName(roomId, username);
        log.info("동작확인 {}", userName);

        return userName;
    }
}