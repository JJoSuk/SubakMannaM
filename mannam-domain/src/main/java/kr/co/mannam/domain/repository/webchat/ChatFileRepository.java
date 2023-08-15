package kr.co.mannam.domain.repository.webchat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.co.mannam.domain.entity.webchat.ChatFile;
import kr.co.mannam.domain.entity.webchat.ChatRoom;

import java.util.List;

@Repository
public interface ChatFileRepository extends JpaRepository<ChatFile, Long> {
    List<ChatFile> findByChatRoom(ChatRoom chatRoom);
}