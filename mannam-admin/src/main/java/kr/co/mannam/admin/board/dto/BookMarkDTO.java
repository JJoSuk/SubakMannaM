package kr.co.mannam.admin.board.dto;

import kr.co.mannam.domain.entity.board.BookMark;
import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.type.board.BoardCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BookMarkDTO {
    private Long id;
    private BoardCategory boardCategory;
    private User user;

    public BookMark toEntity(){
        return BookMark.builder()
                .id(id)
                .boardCategory(boardCategory)
                .user(user)
                .build();
    }
}
