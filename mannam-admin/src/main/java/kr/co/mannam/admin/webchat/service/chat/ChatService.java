package kr.co.mannam.admin.webchat.service.chat;

import kr.co.mannam.admin.webchat.dto.chat.ChatRoomDto;
import kr.co.mannam.domain.entity.webchat.ChatRoom;

import java.util.ArrayList;
import java.util.List;

public interface ChatService {

    public List<ChatRoomDto> findAllRoom(); // 모든 채팅방을 검색
    public ChatRoomDto findRoomById(String roomId); // 채팅방 ID로 채팅방 검색
    public ChatRoomDto createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt); // 새로운 채팅방 생성
    public void plusUserCnt(String roomId); // 채팅방의 사용자 수 조절
    public void minusUserCnt(String roomId); // 위와 동일
    public boolean chkRoomUserCnt(String roomId); // 채팅방의 사용자 수 확인
    public String addUser(String roomId, String userName); // 채팅방에 사용자 추가
    public void delUser(String roomId, String userUUID); // 채팅방에 사용자 삭제
    public void delChatRoom(String roomId); // 채팅방 삭제
    public ArrayList<String> getUserList(String roomId); // 채팅방 전체 userlist 조회
    public String getUserName(String roomId, String userUUID); // 채팅방 userName 조회
    public boolean confirmPwd(String roomId, String roomPwd); // 채팅방 비밀번호 조회
    public String isDuplicateName(String roomId, String username);

    void updateRoom(ChatRoom configData);
}
