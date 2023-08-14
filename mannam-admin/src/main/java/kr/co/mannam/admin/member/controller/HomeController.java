package kr.co.mannam.admin.member.controller;

import kr.co.mannam.admin.board.dto.BoardDTO;
import kr.co.mannam.admin.board.service.BoardService;
import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.repository.board.BoardRepository;
import kr.co.mannam.type.board.BoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String home(HttpSession session,Model model) {
        session.setAttribute("category",BoardCategory.values());

        List<BoardEntity> boardList = boardRepository.findLatestFive(PageRequest.of(0, 5));
        model.addAttribute("boardList",boardList);

        /**배너 - 오늘날짜의 인기 게시물 top5 **/
        List<BoardEntity> todayTopFiveList = boardService.findTodayTopFive();
        model.addAttribute("todayTopFiveList", todayTopFiveList);
        System.out.println("todayTopFiveList = " + todayTopFiveList);

        List<BoardEntity> NoticList = boardRepository.findLatestNoticeBoards(PageRequest.of(0, 5));
        System.out.println("NoticList = " + NoticList);
        model.addAttribute("NoticList",NoticList);

        return "user/index";
    }


}
