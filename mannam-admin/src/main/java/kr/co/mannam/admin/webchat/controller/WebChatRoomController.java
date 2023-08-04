package kr.co.mannam.admin.webchat.controller;

import kr.co.mannam.admin.webchat.service.WebChatRoomService;
import kr.co.mannam.domain.entity.webchat.WebChatRoomEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebChatRoomController {

    private final WebChatRoomService webChatRoomService;

    /**
     * 채팅 리스트 화면
     */
    @GetMapping("/")
    public String goChatRoom(Model model) {
        List<WebChatRoomEntity> chatRooms = webChatRoomService.findAllRooms();
        Collections.reverse(chatRooms);
        model.addAttribute("list", chatRooms);
        return "roomlist.html";
    }

    /**
     * 채팅방 생성
     */
    @PostMapping("/pub/chat/createroom")
    public String createRoom(@RequestParam String name, RedirectAttributes attributes) {
        WebChatRoomEntity room = webChatRoomService.createChatRoom(name);
        log.info("CREATE Chat Room {}", room);

        attributes.addFlashAttribute("roomName", room);

        return "redirect:/";
    }

    /**
     * 채팅방 입장 화면
     */
    @GetMapping("/pub/chat/room")
    public String roomDetail(Model model, @RequestParam String roomId) {
        log.info("roomId {}", roomId);

        model.addAttribute("room", webChatRoomService.findRoomById(roomId));
        return "roomlist.html";
    }
}