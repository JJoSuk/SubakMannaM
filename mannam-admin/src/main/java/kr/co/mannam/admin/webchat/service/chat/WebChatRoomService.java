package kr.co.mannam.admin.webchat.service.chat;

import kr.co.mannam.admin.webchat.dto.chat.WebChatRoomDTO;
import kr.co.mannam.admin.webchat.dto.chat.WebChatRoomMap;
import kr.co.mannam.admin.webchat.service.file.FileService;
import kr.co.mannam.admin.webchat.type.chat.ChatType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebChatRoomService {

    private final MsgChatService msgChatService;

    // 채팅방 삭제에 따른 채팅방의 사진 삭제를 위한 fileService 선언
    private final FileService fileService;

    // 전체 채팅방 조회
    public List<WebChatRoomDTO> findAllRoom(){
        // 채팅방 생성 순서를 최근순으로 반환
        List<WebChatRoomDTO> chatRooms = new ArrayList<>(WebChatRoomMap.getInstance().getChatRooms().values());
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    // roomID 기준으로 채팅방 찾기
    public WebChatRoomDTO findRoomById(String roomId){
        return WebChatRoomMap.getInstance().getChatRooms().get(roomId);
    }

    // roomName 로 채팅방 만들기----------------------------------임시
    public WebChatRoomDTO createChatRoom(String roomName, String roomPass, boolean roomLock, int maxUserCnt, String chatType){
        // 채팅방 타입에 따라서 사용되는 Service 구분을 제거하고 msgChatService만 사용하도록 수정
        WebChatRoomDTO room = msgChatService.createChatRoom(roomName, roomPass, roomLock, maxUserCnt);
        return room;
    }

    // 채팅방 비밀번호 조회
    public boolean confirmPass(String roomId, String roomPass) {

        return roomPass.equals(WebChatRoomMap.getInstance().getChatRooms().get(roomId).getRoomPass());

    }

    // 채팅방 인원+1
    public void plusUserCnt(String roomId){

        log.info("cnt {}",WebChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());

        WebChatRoomDTO room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount()+1);
    }

    // 채팅방 인원-1
    public void minusUserCnt(String roomId){
        WebChatRoomDTO room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomId){

        WebChatRoomDTO room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);

        if (room.getUserCount() + 1 > room.getMaxUserCnt()) {
            return false;
        }
        return true;
    }

    // 채팅방 삭제
    public void delChatRoom(String roomId){

        try {
            // 채팅방 타입에 따라서 단순히 채팅방만 삭제할지 업로드된 파일도 삭제할지 결정
            WebChatRoomMap.getInstance().getChatRooms().remove(roomId);

            if (WebChatRoomMap.getInstance().getChatRooms().get(roomId).getChatType().equals(ChatType.MSG)) { // MSG 채팅방은 사진도 추가 삭제
                // 채팅방 안에 있는 파일 삭제
                fileService.deleteFileDir(roomId);
            }
            log.info("삭제 완료 roomId : {}", roomId);
        } catch (Exception e) { // 만약에 예외 발생시 확인하기 위해서 try catch
            System.out.println(e.getMessage());
        }
    }
}