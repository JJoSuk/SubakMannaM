package kr.co.mannam.admin.webchat.service.chat;

import kr.co.mannam.admin.webchat.service.file.FileService;
import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.domain.entity.webchat.ChatRoom;
import kr.co.mannam.domain.repository.member.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.mannam.admin.webchat.dto.chat.ChatRoomDto;
import kr.co.mannam.domain.repository.webchat.ChatRoomRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    // 채팅방 등록된 사진 삭제를 위한 fileService 선언
    private final FileService fileService;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    private Map<String, ChatRoomDto> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    // 전체 채팅방 조회
    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomDto> findAllRoom() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream().map(chatRoom -> {
            ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                    .roomId(chatRoom.getRoomId())
                    .roomName(chatRoom.getRoomName())
                    .roomPwd(chatRoom.getRoomPwd())
                    .secretChk(chatRoom.isSecretChk())
                    .userCount(chatRoom.getUserCount())
                    .maxUserCnt(chatRoom.getMaxUserCnt())
                    // userlist는 따로 처리해야 합니다.
                    .build();
            return chatRoomDto;
        }).collect(Collectors.toList());
    }

    // roomID 기준으로 채팅방 찾기
    @Transactional(readOnly = true)
    @Override
    public ChatRoomDto findRoomById(String roomId) {
        return chatRoomRepository.findById(roomId)
                .map(chatRoom -> {
                    ChatRoomDto dto = ChatRoomDto.builder()
                            .roomId(chatRoom.getRoomId())
                            .roomName(chatRoom.getRoomName())
                            .roomPwd(chatRoom.getRoomPwd())
                            .secretChk(chatRoom.isSecretChk())
                            .userCount(chatRoom.getUserCount())
                            .maxUserCnt(chatRoom.getMaxUserCnt())
                            // userlist는 따로 처리해야 합니다. 현재는 null로 설정합니다.
                            .userlist(null)
                            .build();
                    return dto;
                })
                .orElse(null);
    }

    // roomName 로 채팅방 만들기
    @Transactional
    @Override
    public ChatRoomDto createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt){

        // DTO 생성
        ChatRoomDto chatRoom = ChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .roomPwd(roomPwd) // 채팅방 패스워드
                .secretChk(secretChk) // 채팅방 잠금 여부
                .userCount(0) // 채팅방 참여 인원수
                .maxUserCnt(maxUserCnt) // 최대 인원수 제한
                .userlist(new HashMap<String, String>())
                .build();

        // Entity 생성
        ChatRoom chatRoomEntity = ChatRoom.builder()
                .roomId(chatRoom.getRoomId())
                .roomName(chatRoom.getRoomName())
                .roomPwd(chatRoom.getRoomPwd())
                .userCount(chatRoom.getUserCount())
                .maxUserCnt(chatRoom.getMaxUserCnt())
                .secretChk(secretChk)
                .build();

        // 데이터베이스에 저장
        chatRoomRepository.save(chatRoomEntity);

        return chatRoom;
    }

    // 채팅방 인원 +1
    @Override
    public void plusUserCnt(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        if (chatRoom != null) {
            chatRoom.setUserCount(userRepository.findByChatRoom_RoomId(roomId).size()); // entity 에 setter 를 설정 안했는데 어떻게 set 을 가져와야 할까
            chatRoomRepository.save(chatRoom);
        }
    }

    // 채팅방 인원 -1
    @Transactional
    @Override
    public void minusUserCnt(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        if (chatRoom != null) {
            chatRoom.setUserCount(userRepository.findByChatRoom_RoomId(roomId).size()); // entity 에 setter 를 설정 안했는데 어떻게 set 을 가져와야 할까
            chatRoomRepository.save(chatRoom);
        }
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    @Transactional(readOnly = true)
    @Override
    public boolean chkRoomUserCnt(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
        return chatRoom.getUserCount() < chatRoom.getMaxUserCnt();
    }

    // 채팅방 유저 리스트에 유저 추가
    @Override
    public String addUser(String roomId, String userName) {
        ChatRoom chatRoomEntity = chatRoomRepository.findById(roomId).orElse(null);
        String userUUID = UUID.randomUUID().toString();
        User user = new User(userUUID, userName, chatRoomEntity);
        if (chatRoomEntity == null) return null;
        chatRoomEntity.addUser(user);
        userRepository.save(user);

        return userUUID;
    }

    @Override
    public String isDuplicateName(String roomId, String username) {
        List<User> usersInRoom = userRepository.findByChatRoom_RoomId(roomId);

        // 람다 생성
        String tmp = username;
        // 람다 표현식 복사본, 이 변수는 불변
        String tmpForLambda = tmp;

        while (usersInRoom.stream().anyMatch(user -> user.getUsername().equals(tmpForLambda))) { // tmp 이 부분 오류 생겼어
            int ranNum = (int) (Math.random() * 100) + 1;
            tmp = username + ranNum;
        }
        return tmp;
    }

    // 채팅방 유저 리스트 삭제
    @Override
    public void delUser(String roomId, String userUUID) {
        User user = userRepository.findByUserUUIDAndChatRoom_RoomId(userUUID, roomId);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    // 채팅방 userName 조회
    @Override
    public String getUserName(String roomId, String userUUID) {
        User user = userRepository.findByUserUUIDAndChatRoom_RoomId(userUUID, roomId);
        return user != null ? user.getUsername() : null;
    }

    // 채팅방 전체 userlist 조회
    @Override
    public ArrayList<String> getUserList(String roomId) {
        List<User> usersInRoom = userRepository.findByChatRoom_RoomId(roomId);
        return usersInRoom.stream().map(User::getUsername).collect(Collectors.toCollection(ArrayList::new));
    }

    // 채팅방 비밀번호 조회
    @Transactional(readOnly = true)
    @Override
    public boolean confirmPwd(String roomId, String roomPwd) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
        return chatRoom.getRoomPwd().equals(roomPwd);
    }

    // 채팅방 삭제
    @Transactional
    @Override
    public void delChatRoom(String roomId) {
        // 채팅방 안에 등록된 파일 삭제
        fileService.deleteFileDir(roomId);
        // 채팅방 삭제
        chatRoomRepository.deleteById(roomId);
    }
}