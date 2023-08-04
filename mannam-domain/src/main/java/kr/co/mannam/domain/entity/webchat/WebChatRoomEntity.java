package kr.co.mannam.domain.entity.webchat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "WebChatRoomEntity")
@AllArgsConstructor
@NoArgsConstructor
public class WebChatRoomEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId; // 채팅방 아이디
    @Column(length = 40)
    private String roomName; // 채팅방 제목
    private Long userCount; // 채팅방 인원

    @ElementCollection
    private Map<String, String> userList = new HashMap<>(); // 채팅방 유저 리스트
}