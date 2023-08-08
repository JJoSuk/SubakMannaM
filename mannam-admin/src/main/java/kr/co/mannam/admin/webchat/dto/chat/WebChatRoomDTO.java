package kr.co.mannam.admin.webchat.dto.chat;

import com.sun.istack.NotNull;
import kr.co.mannam.admin.webchat.type.chat.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebChatRoomDTO {

    @NotNull
    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 제목
    private int userCount; // 채팅방 인원수
    private int maxUserCnt; // 채팅방 최대 인원 제한
    private String roomPass; // 채팅방 삭제시 필요한 pwd
    private boolean roomLock; // 채팅방 잠금 여부
    private ChatType chatType;

    // 이 부분은 왜 이걸 사용하는지 모르겠음. 설명이 필요함!
    public ConcurrentMap<String, ?> userList = new ConcurrentHashMap<>();
}
