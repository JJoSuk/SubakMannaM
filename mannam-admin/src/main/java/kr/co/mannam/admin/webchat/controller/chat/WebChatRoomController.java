package kr.co.mannam.admin.webchat.controller.chat;

import kr.co.mannam.admin.webchat.dto.chat.WebChatRoomDTO;
import kr.co.mannam.admin.webchat.dto.chat.WebChatRoomMap;
import kr.co.mannam.admin.webchat.service.chat.WebChatRoomService;
import kr.co.mannam.admin.webchat.type.chat.ChatType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebChatRoomController {

    private final WebChatRoomService webChatRoomService;

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPass") String roomPass,
                             @RequestParam("roomLock") String roomLock,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes attributes) {

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        WebChatRoomDTO room;

        room = webChatRoomService.createChatRoom(name, roomPass, Boolean.parseBoolean(roomLock), Integer.parseInt(maxUserCnt), chatType);

        log.info("CREATE Chat Room [{}]", room);

        attributes.addFlashAttribute("roomName", room);
        return "redirect:/";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId) {

        // 사용자가 요청한 roomId에 해당하는 채팅방 정보를 가져옴
        WebChatRoomDTO room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);

        // 해당 채팅방 정보를 모델에 추가
        model.addAttribute("room", room);

        // 채팅방의 유형에 따라 적절한 뷰를 반환
        if (ChatType.MSG.equals(room.getChatType())) {
            return "chatroom";
        } else {
            // 준비되지 않은 기능에 대한 로직은 주석 처리
            return "feature_not_ready";  // 예: 아직 준비되지 않은 기능에 대한 안내 페이지
        }
    }

    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPass/{roomId}")
    @ResponseBody
    public boolean confirmPass(@PathVariable String roomId, @RequestParam String roomPass){

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return webChatRoomService.confirmPass(roomId, roomPass);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId){

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        webChatRoomService.delChatRoom(roomId);

        return "redirect:/";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){

        return webChatRoomService.chkRoomUserCnt(roomId);
    }
}