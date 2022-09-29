package com.nappy.burger.repository.burger;

import com.nappy.burger.domain.burger.BurgerImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BurgerImgRepository extends JpaRepository<BurgerImg, Long> {

    List<BurgerImg> findByBurgerIdOrderByIdAAsc(Long burgerId);

    BurgerImg findByBurgerIdAndRepimgYn(Long burgerId, String repimgYn);
}
