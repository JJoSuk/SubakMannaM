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

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    Page<BoardEntity> findByBoardCategory(Pageable pageable, BoardCategory category);

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

    /** 인기 게시판 검색 (추천수 5 이상)**/
    @Query("SELECT b FROM BoardEntity b WHERE b.boardTitle LIKE %:keyword% AND b.likeCount >= 5 ORDER BY b.likeCount ASC")
    Page<BoardEntity> findByBoardTitleContainingAndHit(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT b FROM BoardEntity b WHERE b.boardContents LIKE %:keyword% AND b.likeCount >= 5 ORDER BY b.likeCount DESC")
    Page<BoardEntity> findByBoardContentsContainingAndHit(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT b FROM BoardEntity b WHERE b.boardWriter LIKE %:keyword% AND b.likeCount >= 5 ORDER BY b.likeCount DESC")
    Page<BoardEntity> findByBoardWriterContainingAndHit(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT b FROM BoardEntity b WHERE b.likeCount >= 5 ORDER BY b.likeCount DESC")
    Page<BoardEntity> findByHit(Pageable pageable);


    @Query("SELECT DISTINCT b.boardCategory FROM BoardEntity b")
    List<BoardCategory> findDistinctBoardCategory();


    Page<BoardEntity> findByBoardTitleContaining(String keyword, Pageable pageable);

    Page<BoardEntity> findByBoardContentsContaining(String keyword, Pageable pageable);

    Page<BoardEntity> findByBoardWriterContaining(String keyword, Pageable pageable);


    @Modifying
    @Query(value = "update BoardEntity b set b.commentCount = :commentCount where b.id = :boardId")
    void updateCommentCount(@Param("boardId") Long boardId, @Param("commentCount") Long commentCount);


    // board 테이블에서 가장 최근 5개를 불러오기
    @Query("SELECT b FROM BoardEntity b ORDER BY b.id DESC")
    List<BoardEntity> findLatestFive(Pageable pageable);

    // board 테이블에서 공지사항 가장 최근 5개를 불러오기
    @Query("SELECT b FROM BoardEntity b WHERE b.boardCategory = 'Notice' ORDER BY b.id DESC")
    List<BoardEntity> findLatestNoticeBoards(Pageable pageable);


    @Query("SELECT b FROM BoardEntity b ORDER BY b.createdTime DESC")
    List<BoardEntity> findLatestFive();


    @Query("SELECT b FROM BoardEntity b WHERE b.likeCount >= 5 AND b.createdTime > :today ORDER BY b.likeCount DESC")
    List<BoardEntity> findTodayTopFive(@Param("today")LocalDateTime today, Pageable pageable);
}













