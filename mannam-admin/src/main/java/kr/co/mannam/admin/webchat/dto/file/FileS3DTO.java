package kr.co.mannam.admin.webchat.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileS3DTO {

    private MultipartFile file; // 파일 업로드

    private String originFileName; // 파일 원본 이름

    private String transaction; // UUID 를 활용한 랜덤한 파일 위치

    private String webChatRoom; // 파일이 올라간 채팅방 ID

    private String s3DataUrl; // 파일 링크

    private String fileDir; // S3 파일 경로

}