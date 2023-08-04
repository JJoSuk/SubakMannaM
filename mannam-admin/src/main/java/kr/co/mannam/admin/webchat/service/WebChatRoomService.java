package kr.co.mannam.admin.webchat.service;

import kr.co.mannam.domain.entity.webchat.WebChatRoomEntity;
import kr.co.mannam.domain.repository.webchat.WebChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebChatRoomService {

    private final WebChatRoomRepository webChatRoomRepository;

    /**
     * 전체 채팅방 조회
     */
    public List<WebChatRoomEntity> findAllRooms() {
        List<WebChatRoomEntity> chatRooms = webChatRoomRepository.findAll();
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    /**
     * roomId 기준으로 채팅방 찾기
     * @param roomId 채팅방 아이디
     * @return
     */
    public WebChatRoomEntity findRoomById(String roomId) {
        return webChatRoomRepository.findById(roomId).orElse(null);
    }

    /**
     * 채팅방 만들기
     * @param roomName 채팅방 방제
     * @return
     */
    public WebChatRoomEntity createChatRoom(String roomName) {
        WebChatRoomEntity chatRoom = new WebChatRoomEntity();
        chatRoom.setRoomName(roomName);
        webChatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    /**
     * 채팅방 인원 +1
     * @param roomId 채팅방 아이디
     */
    public void plusUserCnt(String roomId) {
        WebChatRoomEntity room = webChatRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            room.setUserCount(room.getUserCount() + 1);
            webChatRoomRepository.save(room);
        }
    }

    /**
     * 채팅방 인원 -1
     * @param roomId 채팅방 아이디
     */
    public void minusUserCnt(String roomId) {
        WebChatRoomEntity room = webChatRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            room.setUserCount(room.getUserCount() - 1);
            webChatRoomRepository.save(room);
        }
    }

    /**
     * 채팅방 유저 리스트에 유저 추가
     * @param roomId 채팅방 아이디
     * @param userName 채팅방 유저 닉네임
     * @return
     */
    public String addUser(String roomId, String userName) {
        WebChatRoomEntity room = webChatRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            String userUUID = UUID.randomUUID().toString();
            room.getUserList().put(userUUID, userName);
            webChatRoomRepository.save(room);

            return userUUID;
        }

        return null;
    }

    /**
     * 채팅방 유저 이름 중복 확인
     * @param roomId 채팅방 아이디
     * @param userName 채팅방 유저 닉네임
     * @return
     */
    public String isDuplicateName(String roomId, String userName) {
        WebChatRoomEntity room = webChatRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            String tmp = userName;
            while (room.getUserList().containsValue(tmp)) {
                int ranNum = (int) (Math.random() * 100) + 1;
                tmp = userName + ranNum;
            }

            return tmp;
        }

        return null;
    }

    /**
     * 채팅방 유저 리스트 삭제
     * @param roomId 채팅방 아이디
     * @param userUUID 유저 고유 번호
     */
    public void delUser(String roomId, String userUUID) {
        WebChatRoomEntity room = webChatRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            room.getUserList().remove(userUUID);
            webChatRoomRepository.save(room);
        }
    }

    /**
     * 채팅방 userName 조회
     * @param roomId 채팅방 아이디
     * @param userUUID 유저 고유 번호
     * @return
     */
    public String getUserName(String roomId, String userUUID) {
        WebChatRoomEntity room = webChatRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            return room.getUserList().get(userUUID);
        }

        return null;
    }

    public List<String> getUserList(String roomId) {
        WebChatRoomEntity room = webChatRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            return new ArrayList<>(room.getUserList().values());
        }

        return new ArrayList<>();
    }
}