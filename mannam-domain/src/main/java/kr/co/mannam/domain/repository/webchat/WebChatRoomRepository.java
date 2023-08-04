package kr.co.mannam.domain.repository.webchat;

import kr.co.mannam.domain.entity.webchat.WebChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebChatRoomRepository extends JpaRepository<WebChatRoomEntity, String> {
}
