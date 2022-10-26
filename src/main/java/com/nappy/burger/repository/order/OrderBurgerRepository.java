package com.nappy.burger.repository.order;

import com.nappy.burger.domain.order.OrderBurger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBurgerRepository extends JpaRepository<OrderBurger, Long>, OrderBurgerRepositoryCustom {
}
