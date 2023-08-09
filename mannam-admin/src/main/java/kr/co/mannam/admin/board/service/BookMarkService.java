package kr.co.mannam.admin.board.service;

import kr.co.mannam.admin.board.dto.BookMarkDTO;
import kr.co.mannam.domain.entity.board.BookMark;
import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.domain.repository.board.BookMarkRepository;
import kr.co.mannam.domain.repository.member.UserRepository;
import kr.co.mannam.type.board.BoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(String userId, List<BoardCategory> boardCategoryList) {
        for (BoardCategory category : boardCategoryList) {
            Optional<BookMark> findBookMark = bookMarkRepository.findByUserIdAndBoardCategory(userId, category);
            if (!(findBookMark.isPresent())) {
                User user = userRepository.findById(userId).get();
                BookMark bookMark = BookMark.builder()
                        .user(user)
                        .boardCategory(category)
                        .build();

                bookMarkRepository.save(bookMark);
            }
        }

    }
}
