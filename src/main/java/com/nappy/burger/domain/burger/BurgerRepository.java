package com.nappy.burger.domain.burger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BurgerRepository extends JpaRepository<Burger, Long>{

    Page<Burger> findByNameContainingOrContentContaining(String name, String content, Pageable pageable);

}
