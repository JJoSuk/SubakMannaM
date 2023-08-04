package kr.co.mannam.admin.member.controller;



import kr.co.mannam.admin.member.dto.ResponseDTO;
import kr.co.mannam.admin.member.service.UserService;
import kr.co.mannam.domain.entity.member.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    // 로그인 화면 이동
    @GetMapping("/auth/login")
    public String login() {
        return "system/login";
    }

    // 로그인 인증 처리
    @PostMapping("/auth/login")
    public @ResponseBody ResponseDTO<?> login(@RequestBody User user
            , HttpSession session) {
        User findUser = userService.getUser(user.getId());

        // 검색결과 유무와 사용자가 입력한 비밀번호 검증
        if (findUser.getId() == null) {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),
                    "아이디가 존재하지 않습니다!!");
        } else {
            // 비번 검증
            if (user.getPassword().equals(findUser.getPassword())) {
                session.setAttribute("principal", findUser);
                return new ResponseDTO<>(HttpStatus.OK.value(), findUser.getUsername() +
                        "님 로그인 성공했습니다!!");
            } else {
                return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),
                        "비밀번호 오류");
            }
        }
    }

    // 로그아웃
    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
