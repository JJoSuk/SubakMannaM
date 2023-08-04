package kr.co.mannam.domain.entity.board;


import demo.mannam_project.board.dto.CommentDTO;
import demo.mannam_project.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "commnet_table")
@NoArgsConstructor
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    /* Board:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @Builder
//    public CommentEntity(Long id, String commentWriter, String commentContents, BoardEntity boardEntity){
//        this.id = id;
//        this.commentWriter = commentWriter;
//        this.commentContents = commentContents;
//        this.boardEntity = boardEntity;
//    }
//
//    public CommentDTO toCommentDTO(){
//        return CommentDTO.builder()
//                .id(id)
//                .commentWriter(commentWriter)
//                .commentContents(commentContents)
//                .commentEntity(this)
//                .build();
//    }
    public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity, User user) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setUser(user);
        return commentEntity;
    }
}
