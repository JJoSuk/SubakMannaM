package kr.co.mannam.domain.entity.member;


import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.entity.board.BookMark;
import kr.co.mannam.domain.entity.board.CommentEntity;
import kr.co.mannam.domain.entity.webchat.ChatRoom;
import kr.co.mannam.type.member.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data // @Getter. @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USERS")
public class User {
    @Id
    private String id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp // 현재 시간이 기본값으로 등록되도록 설정
    private Timestamp createDate;

    @Column(name = "user_uuid", nullable = false)
    private String userUUID;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntitieList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntitieList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BookMark> bookMarkList = new ArrayList<>();

    public User(String userUUID, String userName, ChatRoom chatRoomEntity){
        this.userUUID = userUUID;
        this.username = userName;
        this.chatRoom = chatRoomEntity;
    }
}
