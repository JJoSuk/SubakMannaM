package kr.co.mannam.admin.webchat.service.file;

import kr.co.mannam.admin.webchat.dto.file.FileS3DTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public interface FileService {

    /**
     * 파일 업로드를 위한 메서드 선언
     * @param file 파일 업로드
     * @param transaction 랜덤한 파일 위치 생성
     * @param roomId 채팅방 아이디
     * @return 파일 업로드 관련 메서드
     */
    FileS3DTO s3File(MultipartFile file, String transaction, String roomId);

    /**
     * 채팅방에 업로드된 모든 파일 삭제
     * @param path deleteFileDir(해당 채팅방 모든 파일 삭제)
     */
    void deleteFileDir(String path);

    /**
     * 컨트롤러에서 받아온 multipartFile -> File 로 변환시켜 저장
     * @param mfile 파일 업로드 처리 메서드
     * @param tmpPath 임시 파일(저장 경로 지정)
     * @return multipartFile -> File
     */
    default File convertMultipartFileToFile(MultipartFile mfile, String tmpPath) throws IOException {
        File file = new File(tmpPath);

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(mfile.getBytes());
            }
            return file;
        }
        throw new IOException();
    }

    // 파일 삭제

    /**
     * 파일 삭제
     * @param file 파일(데이터)
     */
    default void removeFile(File file){
        file.delete();
    }

    /**
     * 경로와 파일 이름을 사용하여 파일의 내용을 바이트 배열로 반환하는 역할
     * @param fileDir 파일이 저장된 경로
     * @param fileName 가져올 파일 이름
     * @return
     */
    ResponseEntity<byte[]> getObject(String fileDir, String fileName) throws IOException;
}