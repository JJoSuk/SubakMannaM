package kr.co.mannam.domain.entity.board;



import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.type.board.BoardCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Table(name = "board_table")
@NoArgsConstructor
public class BoardEntity extends BaseEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column
    private String boardTitle;

    @Column(length= 100000000)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int likeCount;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;


    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntitieList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Long commentCount; // 댓글 수 추가



    @Builder
    public BoardEntity(Long id, String boardWriter, String boardTitle, String boardContents, int boardHits, User user, BoardCategory boardCategory, int likeCount,Long commentCount){
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.user = user;
        this.boardCategory = boardCategory;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

//    public BoardDTO toBoardDTO(){
//        return BoardDTO.builder()
//                .id(id)
//                .boardWriter(boardWriter)
//                .boardTitle(boardTitle)
//                .boardContents(boardContents)
//                .boardHits(boardHits)
//                .boardCreatedTime(super.getCreatedTime())
//                .boardUpdatedTime(super.getUpdatedTime())
//                .user(user)
//                .build();
//    }

//    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
//        BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntity.setBoardPass(boardDTO.getBoardPass());
//        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
//        boardEntity.setBoardContents(boardDTO.getBoardContents());
//        boardEntity.setBoardHits(0);
//        return boardEntity;
//    }
//
//    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
//        BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setId(boardDTO.getId());
//        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntity.setBoardPass(boardDTO.getBoardPass());
//        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
//        boardEntity.setBoardContents(boardDTO.getBoardContents());
//        boardEntity.setBoardHits(boardDTO.getBoardHits());
//        return boardEntity;
//    }
}










