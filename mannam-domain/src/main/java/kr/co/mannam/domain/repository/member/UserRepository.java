package kr.co.mannam.domain.repository.member;

import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.domain.entity.webchat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // SQL => JPQL => 쿼리메서드
    // find + 엔티티명 + By + 변수명 ==> findUserByUsername
    // 엔티티명은 생략가능 : findByUsername

    // SELECT * FROM users WHERE username = ?1;
    Optional<User> findById(String id);

    List<User> findByChatRoom_RoomId(String roomId);

    User findByUserUUIDAndChatRoom_RoomId(String userUUID, String roomId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userUUID = :userUUID, u.chatRoom = :chatRoomEntity WHERE u.username = :userName")
    void updateUserUUIDAndChatRoomEntityByUsername(

            @Param("userUUID") String userUUID,
            @Param("userName") String userName,
            @Param("chatRoomEntity") ChatRoom chatRoomEntity
    );
    User findByUsername(String userName);
}
