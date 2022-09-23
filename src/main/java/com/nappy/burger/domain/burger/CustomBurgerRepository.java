package com.nappy.burger.domain.burger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomBurgerRepository extends JpaRepository<CustomBurger, Long> {

    Page<CustomBurger> findByNameContainingOrPattyContaining(String name, String patty, String bread, String source, String additional, Pageable pageable);
}
