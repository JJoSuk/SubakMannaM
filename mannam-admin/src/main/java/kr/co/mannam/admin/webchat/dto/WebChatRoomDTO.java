package kr.co.mannam.admin.webchat.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Data
public class WebChatRoomDTO {

    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 제목
    private int userCount; // 채팅방 인원수

    private Map<String, String> userList = new HashMap<>();

    // 기본 생성자
    public WebChatRoomDTO() {
        this.roomId = UUID.randomUUID().toString();
    }

    // 생성자
    public WebChatRoomDTO(String roomName) {
        this();
        this.roomName = roomName;
    }

    // 채팅방 생성 정적 메소드
    public static WebChatRoomDTO create(String roomName) {
        return new WebChatRoomDTO(roomName);
    }

    // 편의성 메소드: 채팅방 유저 추가
    public void addUser(String userUUID, String userName) {
        userList.put(userUUID, userName);
    }

    // 편의성 메소드: 채팅방 유저 삭제
    public void removeUser(String userUUID) {
        userList.remove(userUUID);
    }
}
