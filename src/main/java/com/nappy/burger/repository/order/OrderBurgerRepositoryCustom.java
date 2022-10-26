package com.nappy.burger.repository.order;

import com.nappy.burger.dto.order.OrderBurgerHistDto;
import com.nappy.burger.dto.order.OrderBurgerSearchDto;
import com.nappy.burger.dto.order.OrderBurgerTypeHistDto;

import java.util.List;

public interface OrderBurgerRepositoryCustom {

    List<OrderBurgerHistDto> getAdminTotalPage(OrderBurgerSearchDto orderBurgerSearchDto);

    List<OrderBurgerTypeHistDto> getAdminTypePage(OrderBurgerSearchDto orderBurgerSearchDto);
}
