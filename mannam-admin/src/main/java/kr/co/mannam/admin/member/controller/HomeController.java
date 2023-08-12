package kr.co.mannam.admin.member.controller;

import kr.co.mannam.admin.board.dto.BoardDTO;
import kr.co.mannam.admin.board.service.BoardService;
import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.repository.board.BoardRepository;
import kr.co.mannam.type.board.BoardCategory;
import lombok.RequiredArgsConstructor;
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

        List<BoardEntity> boardList = boardRepository.findLatestFive();
        System.out.println("boardList = " + boardList);
        model.addAttribute("boardList",boardList);

        return "user/index";
    }


}
