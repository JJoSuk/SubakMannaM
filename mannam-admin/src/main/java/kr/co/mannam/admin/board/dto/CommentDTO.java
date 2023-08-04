package kr.co.mannam.admin.board.dto;


import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.entity.board.CommentEntity;
import kr.co.mannam.domain.entity.member.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;
    private BoardEntity boardEntity;
    private String userId;

//    @Builder
//    public CommentDTO(Long id, String commentWriter, String commentContents, CommentEntity commentEntity){
//        this.id = id;
//        this.commentWriter = commentWriter;
//        this.commentContents = commentContents;
//        this.boardId = commentEntity.getBoardEntity().getId();
//        this.commentCreatedTime =  commentEntity.getCreatedTime();
//    }
//
//    public CommentEntity toCommentEntity(){
//        return CommentEntity.builder()
//                .id(id)
//                .commentWriter(commentWriter)
//                .commentContents(commentContents)
//                .boardEntity(boardEntity)
//                .build();
//    }

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
//        commentDTO.setBoardId(commentEntity.getBoardEntity().getId()); // 이 경우 Service 메소드에 @Transactional
        commentDTO.setBoardId(boardId);
        commentDTO.setUserId(commentEntity.getUser().getId());
        return commentDTO;
    }

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity, User user) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setUser(user);
        return commentEntity;
    }
}
