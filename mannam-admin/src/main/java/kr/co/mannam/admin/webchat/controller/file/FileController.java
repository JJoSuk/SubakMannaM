package kr.co.mannam.admin.webchat.controller.file;

import kr.co.mannam.admin.webchat.dto.file.FileS3DTO;
import kr.co.mannam.admin.webchat.service.file.S3FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/s3")
@Slf4j
public class FileController {

    @Autowired
    private S3FileService fileService;

    /**
     * 프론트에서 ajax 를 통해 /upload 로 MultipartFile 형태로 파일과 roomId 를 전달 받고, file 을 s3File 메서드를 통해 업로드한다.
     * @param file 파일 업로드
     * @param roomId 채팅방 아이디
     * @return
     */
    @PostMapping("/upload")
    public FileS3DTO s3File(@RequestParam("file") MultipartFile file, @RequestParam("roomId")String roomId){

        FileS3DTO fileReq = fileService.s3File(file, UUID.randomUUID().toString(), roomId);
        log.info("최종 upload Data {}", fileReq);

        // fileReq 객체 리턴
        return fileReq;
    }

    /**
     * 클라이언트가 요청한 파일을 찾아서 다운로드할 수 있도록 HTTP 응답을 반환하는 역할
     * @param fileName 다운로드할 파일 이름
     * @param fileDir S3 버킷 내의 경로
     * @return
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName, @RequestParam("fileDir")String fileDir) {
        log.info("fileDir : fileName [{} : {}]", fileDir, fileName);
        try {
            // 변환된 byte, httpHeader 와 HttpStatus 가 포함된 ResponseEntity 객체를 return 한다.
            return fileService.getObject(fileDir, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}