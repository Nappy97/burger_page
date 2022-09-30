package com.nappy.burger.repository.cart;

import com.nappy.burger.domain.cart.CartBurger;
import com.nappy.burger.dto.cart.CartListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartBurgerRepository extends JpaRepository<CartBurger, Long> {

    CartBurger findByCartIdAndBurgerId(Long cartId, Long burgerId);

    @Query("select new com.nappy.burger.dto.cart.CartListDto(cb.id, b.burgerName, b.price, cb.count, bi.imgUrl )" +
            "from CartBurger cb, BurgerImg bi " +
            "join cb.burger b " +
            "where cb.cart.id = :cartId " +
            "and bi.burger.id = cb.burger.id " +
            "and bi.repimgYn = 'Y' " +
            "order by cb.regTime desc ")
    List<CartListDto> findCartListDto(@Param("cartId") Long cartId);
}
