package kr.co.mannam.admin.webchat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import kr.co.mannam.admin.webchat.service.chat.ChatService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainChatController { // 메인 채팅 페이지와 관련된 HTTP 요청을 처리하는 컨트롤러

    private final ChatService chatService;

    // 채팅방 리스트 페이지로 이동
    @GetMapping("/chat")
    public String goChatRoom(Model model){

        model.addAttribute("list", chatService.findAllRoom());
        // model.addAttribute("user", "hey");
        log.info("SHOW ALL ChatList {}", chatService.findAllRoom());
        return "roomlist";
    }
}
