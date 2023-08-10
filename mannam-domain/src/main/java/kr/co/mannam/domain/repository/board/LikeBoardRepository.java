package kr.co.mannam.domain.repository.board;

import kr.co.mannam.domain.entity.board.LikeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    boolean existsByBoard_IdAndUser_Id(Long boardId, String UserId);

    void deleteByBoard_IdAndUser_Id(Long boardId, String userId);
}
