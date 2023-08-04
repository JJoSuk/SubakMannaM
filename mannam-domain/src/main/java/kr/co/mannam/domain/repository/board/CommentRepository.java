package kr.co.mannam.domain.repository.board;

import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.entity.board.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}