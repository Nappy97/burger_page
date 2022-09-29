package com.nappy.burger.repository.burger;

import com.nappy.burger.domain.burger.Burger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BurgerRepository extends JpaRepository<Burger, Long>, BurgerRepositoryCustom{

}
