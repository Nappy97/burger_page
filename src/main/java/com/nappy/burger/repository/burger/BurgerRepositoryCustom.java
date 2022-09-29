package com.nappy.burger.repository.burger;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.dto.burger.BurgerSearchDto;
import com.nappy.burger.dto.burger.MainBurgerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BurgerRepositoryCustom {

    Page<Burger> getAdminBurgerPage(BurgerSearchDto  burgerSearchDto, Pageable pageable);

    // @QueryProjection 을 이용하여 바로 DTO 반환
    Page<MainBurgerDto> getMainBurgerPage(BurgerSearchDto burgerSearchDto, Pageable pageable);
}
