package kr.co.mannam.domain.repository.webmap;

import kr.co.mannam.domain.entity.webmap.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long > {


    Optional<Mark> findByMarkname(String markname);

    Mark findByMid(Long mid);

//    @Query("SELECT mark.mid, mark.markname, mark.writer, mark.markimage , mark.markimagepath , mark.markaddress , mark.markainfo , mark.category  , mark.tel  , mark.latitude  , mark.longitude FROM MARK mark WHERE mark.user_id LIKE %?1%")
    List<Mark.MarkMapping> findMarkByUser_id(String userid);

    void deleteByMid(Long mid);

    List<Mark.MarkMapping> findMarkByMid(Long mid);

    // 해당 사용자의 외래 키 값을 null로 업데이트하는 쿼리 메서드
    @Modifying
    @Query("UPDATE Mark m SET m.user = null WHERE m.user.id = :userId")
    void updateUserIdToNull(@Param("userId") String userId);
}


