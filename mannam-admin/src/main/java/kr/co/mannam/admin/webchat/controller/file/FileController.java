package kr.co.mannam.admin.webchat.controller.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import kr.co.mannam.admin.webchat.dto.file.FileUploadDto;
import kr.co.mannam.admin.webchat.service.file.S3FileService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
@Slf4j
public class FileController { // 파일 업로드 및 다운로드와 관련된 HTTP 요청을 처리하는 컨트롤러

    private final S3FileService fileService;

    // 파일을 업로드하는 메서드. 여기서는 S3 서비스와 연동하여 파일을 저장
    @PostMapping("/upload")
    public FileUploadDto uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("roomId") String roomId) {

        FileUploadDto fileReq = fileService.uploadFile(file, UUID.randomUUID().toString(), roomId);
        log.info("최종 upload Data {}", fileReq);

        // fileReq 객체 리턴
        return fileReq;
    }

    // 파일을 다운로드
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName, @RequestParam("fileDir") String fileDir) {
        log.info("fileDir : fileName [{} : {}]", fileDir, fileName);

        try {
            // 변환된 byte, httpHeader 와 HttpStatus 가 포함된 ResponseEntity 객체를 return 한다.
            return fileService.getObject(fileDir, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}