package kr.co.mannam.domain.repository.board;


import demo.mannam_project.board.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
