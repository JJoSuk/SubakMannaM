package kr.co.mannam.admin.webchat.controller.chat;

import kr.co.mannam.admin.member.dto.ResponseDTO;
import kr.co.mannam.domain.entity.webchat.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import kr.co.mannam.admin.webchat.dto.chat.ChatRoomDto;
import kr.co.mannam.admin.webchat.service.chat.ChatService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController { // 채팅방 관련 HTTP 요청을 처리하는 컨트롤러

    // ChatRepository Bean 가져오기
    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String roomName,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("secretChk") String secretChk,
                             RedirectAttributes attr) {

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDto room;

        room = chatService.createChatRoom(roomName, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt));
        log.info("CREATE Chat Room {}", room);
        attr.addFlashAttribute("roomName", room);

        return "redirect:/chat";
    }

    // 채팅방 리스트 페이지로 이동
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId) {
        if (roomId == null || roomId.isEmpty()) {
            // Handle the error, e.g., return to the main page with an error message
            return "redirect:/chat?error=InvalidRoomId";
        }

        log.info("roomId {}", roomId);

        ChatRoomDto room = chatService.findRoomById(roomId);
        model.addAttribute("room", room);

        return "chatroom";
    }

    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd) {

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatService.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId) {

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatService.delChatRoom(roomId);

        return "redirect:/chat";
    }

    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId) {

        return chatService.chkRoomUserCnt(roomId);
    }

    @PutMapping("/chat/updateRoom")
    public @ResponseBody ResponseDTO<?> updateChatRoom(@RequestBody ChatRoom configData) {

        chatService.updateRoom(configData);

        return new ResponseDTO<>(HttpStatus.OK.value(),
                configData.getRoomName() + "번 방을 수정했습니다!!");
    }
}