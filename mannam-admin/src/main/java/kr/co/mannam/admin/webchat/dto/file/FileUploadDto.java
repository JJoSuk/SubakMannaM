package kr.co.mannam.admin.webchat.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FileUploadDto { // 파일 업로드에 대한 정보

    private MultipartFile file; // 업로드할 파일

    private String originFileName; // 파일 원본 이름

    private String transaction; // UUID를 사용하여 생성된 랜덤 파일 위치

    private String chatRoom; // 파일이 업로드된 채팅방 ID

    private String s3DataUrl; // 파일의 S3 URL 링크

    private String fileDir; // S3에서의 파일 경로
}