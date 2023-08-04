package kr.co.mannam.admin.board.dto;



import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.entity.member.User;
import lombok.*;

import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean,         Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
// @AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private User user;

    @Builder
    public BoardDTO(Long id,
                    String boardWriter,
                    String boardTitle,
                    String boardContents,
                    int boardHits,
                    LocalDateTime boardCreatedTime,
                    LocalDateTime boardUpdatedTime,
                    User user){
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        this.boardUpdatedTime = boardUpdatedTime;
        this.user = user;
    }
    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .id(id)
                .boardWriter(boardWriter)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .boardHits(boardHits)
                .user(user)
                .build();
    }


//    public BoardEntity toUpdateEntity() {
//        return BoardEntity.builder()
//                .id(id)
//                .boardWriter(boardWriter)
//                .boardTitle(boardTitle)
//                .boardContents(boardContents)
//                .boardHits(boardHits)
//                .user(user)
//                .build();
//    }

//    public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
//        this.id = id;
//        this.boardWriter = boardWriter;
//        this.boardTitle = boardTitle;
//        this.boardHits = boardHits;
//        this.boardCreatedTime = boardCreatedTime;
//    }
//
//    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setId(boardEntity.getId());
//        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
//        boardDTO.setBoardPass(boardEntity.getBoardPass());
//        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
//        boardDTO.setBoardContents(boardEntity.getBoardContents());
//        boardDTO.setBoardHits(boardEntity.getBoardHits());
//        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
//        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
//
//        return boardDTO;
//    }
}