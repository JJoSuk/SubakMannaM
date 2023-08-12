package kr.co.mannam.domain.repository.board;

import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.entity.board.CommentEntity;
import kr.co.mannam.domain.entity.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

}