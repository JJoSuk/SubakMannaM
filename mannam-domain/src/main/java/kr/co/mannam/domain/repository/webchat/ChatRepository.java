package kr.co.mannam.domain.repository.webchat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.co.mannam.domain.entity.webchat.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}