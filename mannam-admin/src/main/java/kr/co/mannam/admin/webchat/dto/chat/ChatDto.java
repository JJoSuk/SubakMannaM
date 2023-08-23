package kr.co.mannam.admin.webchat.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    // 메시지  타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK, LEAVE;
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 채팅방 번호
    private String sender; // 메시지를 보낸 사람
    private String message; // 채팅 메시지 내용
    private String time; // 메시지 발송 시간

    /* 파일 업로드 관련 변수 */
    private String s3DataUrl; // 파일 업로드 URL (S3)
    private String fileName; // 파일 이름
    private String fileDir; // S3의 파일 경로
}