package kr.co.mannam.domain.repository.board;


import kr.co.mannam.domain.entity.board.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
