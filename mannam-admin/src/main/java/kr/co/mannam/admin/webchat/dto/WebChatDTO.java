package kr.co.mannam.admin.webchat.dto;

import kr.co.mannam.admin.webchat.type.webchat.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebChatDTO {

    private String roomId; // 채팅방 번호
    private String sender; // 채팅 보낸 사람
    private String message; // 채팅 메시지
    private String time; // 채팅 보낸 시간
    private MessageType type; // 메시지 타입
}