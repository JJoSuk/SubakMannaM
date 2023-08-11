package kr.co.mannam.admin.board.controller;

import kr.co.mannam.admin.board.service.BookMarkService;
import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.domain.repository.member.UserRepository;
import kr.co.mannam.type.board.BoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookMarkController {
    private final BookMarkService bookMarkService;
    private final UserRepository userRepository;

    @PostMapping("/save/{userId}")
    public String save(@RequestParam List<BoardCategory> boardCategoryList,
                       @PathVariable String userId) {
        bookMarkService.save(userId, boardCategoryList);

        return "redirect:/bookmark/update/"+userId;
    }
    @GetMapping("/update/{userId}")
    public String updateUser(@PathVariable String userId,
                       HttpServletRequest request,
                       HttpSession session) {
        // 세션의 유저 정보 업데이트
        User updatedUser = userRepository.findById(userId).get();
        session.setAttribute("principal", updatedUser);
        System.out.println("updatedUser.getBookMarkList() = " + updatedUser.getBookMarkList());

        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }

    @PostMapping("/delete/{userId}")
    public String delete(@RequestParam BoardCategory category,
                       @PathVariable String userId) {
        bookMarkService.delete(userId, category);

        return "redirect:/bookmark/update/"+userId;
    }
}
