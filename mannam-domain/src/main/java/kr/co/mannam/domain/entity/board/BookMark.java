package kr.co.mannam.domain.entity.board;

import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.type.board.BoardCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "board_bookmark")
@NoArgsConstructor
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public BookMark(Long id, BoardCategory boardCategory, User user) {
        this.id = id;
        this.boardCategory = boardCategory;
        this.user = user;
    }
}
