package kr.co.mannam.admin.webchat.dto.chat;

import kr.co.mannam.admin.webchat.type.chat.MessageType;
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

    // 업로드 관련 변수
    private String s3DataUrl; // 파일 업로드 url
    private String fileName; // 파일 이름
    private String fileDir; // s3 파일 경로
}