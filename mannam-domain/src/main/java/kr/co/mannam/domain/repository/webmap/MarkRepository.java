package kr.co.mannam.domain.repository.webmap;

import kr.co.mannam.domain.entity.webmap.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long > {


    Optional<Mark> findByMarkname(String markname);

    Mark findByMid(Long mid);

//    @Query("SELECT mark.mid, mark.markname, mark.writer, mark.markimage , mark.markimagepath , mark.markaddress , mark.markainfo , mark.category  , mark.tel  , mark.latitude  , mark.longitude FROM MARK mark WHERE mark.user_id LIKE %?1%")
    List<Mark.MarkMapping> findMarkByUser_id(String userid);

    void deleteByMid(Long mid);

    List<Mark.MarkMapping> findMarkByMid(Long mid);
}


