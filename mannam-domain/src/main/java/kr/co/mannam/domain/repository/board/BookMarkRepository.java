package kr.co.mannam.domain.repository.board;

import kr.co.mannam.domain.entity.board.BookMark;
import kr.co.mannam.type.board.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByUserIdAndBoardCategory(String userId, BoardCategory category);
}
