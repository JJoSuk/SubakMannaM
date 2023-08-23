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
public class MainChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String goChatRoom(Model model) {

        model.addAttribute("list", chatService.findAllRoom());
        log.info("SHOW ALL ChatList {}", chatService.findAllRoom());
        return "roomlist";
    }

}


