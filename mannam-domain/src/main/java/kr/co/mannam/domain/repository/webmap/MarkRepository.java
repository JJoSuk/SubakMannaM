package kr.co.mannam.domain.repository.webmap;


import kr.co.mannam.domain.entity.webmap.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long > {


    Optional<Mark> findByMarkname(String markname);

    Mark findByMid(Long mid);

}
