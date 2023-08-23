package kr.co.mannam.domain.repository.webchat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.co.mannam.domain.entity.webchat.ChatRoom;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional <Object> findByRoomId(String roomId);
}