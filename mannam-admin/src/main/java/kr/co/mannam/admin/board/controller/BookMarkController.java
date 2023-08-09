package kr.co.mannam.admin.board.controller;

import kr.co.mannam.admin.board.service.BookMarkService;
import kr.co.mannam.type.board.BoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookMarkController {
    private final BookMarkService bookMarkService;

    @PostMapping("/save/{userId}")
    public String save(@RequestParam List<BoardCategory> boardCategoryList,
                       @PathVariable String userId,
                       HttpServletRequest request) {
        bookMarkService.save(userId, boardCategoryList);

        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
}
