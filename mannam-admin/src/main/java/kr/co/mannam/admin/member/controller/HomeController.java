package kr.co.mannam.admin.member.controller;

import kr.co.mannam.type.board.BoardCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        session.setAttribute("category",BoardCategory.values());
        return "user/index";
    }


}
