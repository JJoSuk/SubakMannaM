package kr.co.mannam.admin.member.controller;

import kr.co.mannam.admin.member.dto.ResponseDTO;
import kr.co.mannam.admin.member.dto.UserDTO;
import kr.co.mannam.admin.member.service.UserService;
import kr.co.mannam.domain.entity.member.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/login")
    public String login(){
        return "user/login1";
    }


    @GetMapping("/auth/register")
    public String registerUser() {
//        System.out.println(9/0);  // 예외처리 테스트
        return "user/login/register";
    }

    @PostMapping("/auth/register")
    public @ResponseBody ResponseDTO<?> insertUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
//        userService.insertUser(user);
//        return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "님 회원 가입 성공 완료!!");

        User user = modelMapper.map(userDTO, User.class);

        // 아이디 중복체크
        User findUser = userService.getUser(user.getId());

        if (findUser.getId() == null){
            userService.insertUser(user);

            return new ResponseDTO<>(HttpStatus.OK.value(),user.getUsername()+"님 회원가입 성공했습니다!!");
        }else {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), user.getUsername()+"님은 이미 회원이십니다");
        }
    }

}


