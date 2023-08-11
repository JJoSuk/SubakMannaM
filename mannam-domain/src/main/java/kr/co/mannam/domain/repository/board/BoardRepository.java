package kr.co.mannam.domain.repository.board;



import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.type.board.BoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Query("SELECT b FROM BoardEntity b WHERE b.boardContents LIKE %:keyword% AND b.boardCategory = :category")
    Page<BoardEntity> findByBoardContentsContainingAndBoardCategory(
            @Param("keyword") String keyword,
            @Param("category") BoardCategory category,
            Pageable pageable
    );

    @Query("SELECT b FROM BoardEntity b WHERE b.boardWriter LIKE %:keyword% AND b.boardCategory = :category")
    Page<BoardEntity> findByBoardWriterContainingAndBoardCategory(
            @Param("keyword") String keyword,
            @Param("category") BoardCategory category,
            Pageable pageable
    );

    /** 좋아요 추가 **/
    @Modifying
    @Query(value = "update BoardEntity board set board.likeCount = board.likeCount + 1 where board.id = :boardId")
    int plusLike(@Param("boardId") Long boardId);

    /** 좋아요 삭제 **/
    @Modifying
    @Query(value = "update BoardEntity board set board.likeCount = board.likeCount - 1 where board.id = :boardId")
    int minusLike(@Param("boardId") Long boardId);


    @Query("SELECT DISTINCT b.boardCategory FROM BoardEntity b")
    List<BoardCategory> findDistinctBoardCategory();


    Page<BoardEntity> findByBoardTitleContaining(String keyword, Pageable pageable);

    Page<BoardEntity> findByBoardContentsContaining(String keyword, Pageable pageable);

    Page<BoardEntity> findByBoardWriterContaining(String keyword, Pageable pageable);
}













