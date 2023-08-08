package kr.co.mannam.admin.webchat.service.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import kr.co.mannam.admin.webchat.dto.file.FileS3DTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileService implements FileService{

    /**
     * AmazonS3 주입받기
     */
    private final AmazonS3 amazonS3;

    /**
     * application-s3.properties 에서 S3 bucket 이름 가져옴
     */
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * application-s3.properties 에서 S3 base url 가져옴
     */
    @Value("${cloud.aws.s3.bucket.url}")
    private String baseUrl;

    /**
     * s3File = MultipartFile 과 transaction, roomId
     * @param file 파일 업로드
     * @param transaction 랜덤한 파일 위치 생성
     * @param roomId 채팅방 아이디
     * @return
     */
    @Override
    public FileS3DTO s3File(MultipartFile file, String transaction, String roomId) {
        try{
            String filename = file.getOriginalFilename(); // 파일 원본 이름
            String key = roomId+"/"+transaction+"/"+filename; // S3 파일 경로

            // 매개변수로 넘어온 multipartFile 을 File 객체로 변환 시켜서 저장하기 위한 메서드
            File convertedFile = convertMultipartFileToFile(file, transaction + filename);

            // 아마존 S3 에 파일 업로드를 위해 사용하는 TransferManagerBuilder
            TransferManager transferManager = TransferManagerBuilder
                    .standard()
                    .withS3Client(amazonS3)
                    .build();

            // bucket 에 key 와 converedFile 을 이용해서 파일 업로드
            Upload upload = transferManager.upload(bucket, key, convertedFile);
            upload.waitForUploadResult();

            // 변환된 File 객체 삭제
            removeFile(convertedFile);

            // uploadDTO 객체 빌드
            FileS3DTO s3FileReq = FileS3DTO.builder()
                    .transaction(transaction)
                    .webChatRoom(roomId)
                    .originFileName(filename)
                    .fileDir(key)
                    .s3DataUrl(baseUrl+"/"+key)
                    .build();

            // uploadDTO 객체 리턴
            return s3FileReq;

        } catch (Exception e) {
            log.error("fileUploadException {}", e.getMessage());
            return null;
        }
    }

    /**
     * s3 에 저장된 파일들은 : roomId/UUID(파일명)/원본 파일명 으로 변경된다.
     * 그래서 path 를 사용해 파일을 삭제하기 위해서 path -> roomId 로 자동변환 되어
     * roomId 를 입력하면 입력된 roomId 에 저장된 파일들은 모두 삭제된다.
     * @param path deleteFileDir(해당 채팅방 모든 파일 삭제)
     */
    @Override
    public void deleteFileDir(String path) {
        for (S3ObjectSummary summary : amazonS3.listObjects(bucket, path).getObjectSummaries()) {
            amazonS3.deleteObject(bucket, summary.getKey());
        }
    }

    /**
     * byte 배열 타입을 return 한다.
     * @param fileDir 파일이 저장된 경로
     * @param fileName 가져올 파일 이름
     * @return
     */
    @Override
    public ResponseEntity<byte[]> getObject(String fileDir, String fileName) throws IOException {
        // bucket 와 fileDir 을 사용해서 S3 에 있는 객체 - object - 를 가져온다.
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucket, fileDir));

        // object 를 S3ObjectInputStream 형태로 변환
        S3ObjectInputStream objectInputStream = object.getObjectContent();

        // 이후 다시 byte 배열 형태로 변환
        // 아마도 파일 전송을 위해서는 다시 byte[] 즉, binary 로 변환해서 전달해야햐기 때문
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        // 여기는 httpHeader 에 파일 다운로드 요청을 하기 위한 내용
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // 지정된 fileName 으로 파일이 다운로드 된다.
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        // 주어진 경로와 파일 이름을 가지고 s3 에서 파일을 가져와 http 응답으로 반환하는 역할.
        // 파일 내용을 byte 변환 -> http 응답 헤더를 설정하여 파일 다운 정보 제공 -> 파일 내용과 헤더를 포함한 http 응답을 반환
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }
}