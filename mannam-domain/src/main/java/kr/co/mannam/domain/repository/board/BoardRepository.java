package kr.co.mannam.domain.repository.board;



import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.type.board.BoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    public Page<BoardEntity> findByBoardCategory(Pageable pageable, BoardCategory category);

//    @Query("SELECT b FROM BoardEntity b WHERE b.boardTitle LIKE %:keyword% AND b.boardCategory = :category")
//    public Page<BoardEntity> findByBoardTitleContainingAndBoardCategory(String keyword, Pageable pageable, BoardCategory category);


    @Query("SELECT b FROM BoardEntity b WHERE b.boardTitle LIKE %:keyword% AND b.boardCategory = :category")
    Page<BoardEntity> findByBoardTitleContainingAndBoardCategory(
            @Param("keyword") String keyword,
            @Param("category") BoardCategory category,
            Pageable pageable
    );
}













