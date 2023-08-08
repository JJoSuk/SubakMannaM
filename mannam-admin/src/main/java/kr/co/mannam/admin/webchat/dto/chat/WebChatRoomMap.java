package kr.co.mannam.admin.webchat.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ChatRoomMap 을 싱글톤으로 정의하는 디자인 패
 * */
@Getter
@Setter
public class WebChatRoomMap {

    private static WebChatRoomMap webChatRoomMap = new WebChatRoomMap();
    // 동시에 여러 채팅방을 관리하기 위한 동시성 맵
    private ConcurrentMap<String, WebChatRoomDTO> chatRooms = new ConcurrentHashMap<>();

    private WebChatRoomMap(){}

    // 유일한 인스턴스를 반환하는 메소드
    // 이 메소드를 통해 유일한 인스턴스 접근 가능
    public static WebChatRoomMap getInstance(){
        return webChatRoomMap;
    }

}
