package kr.co.mannam.admin.webchat.dto.chat;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class ChatRoomDto { // 채팅방에 대한 정보
    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private int userCount; // 채팅방에 참여하고 있는 사용자 수
    private int maxUserCnt; // 채팅방의 최대 인원 제한

    private String roomPwd; // 채팅방 삭제시 필요한 비밀번호
    private boolean secretChk; // 채팅방 잠금 여부

    private HashMap<String, String> userlist; // 채팅방에 참여하고 있는 사용자 목록 (HashMap 형태)
}