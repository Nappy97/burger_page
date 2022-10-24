package com.nappy.burger.dto.order;

import com.nappy.burger.domain.order.OrderStatus;
import com.nappy.burger.domain.user.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdminOrderDto {

    private Long orderId;

    private String orderDate;

    private OrderStatus orderStatus;

    private String imgUrl;
    private User user;

    private List<OrderBurgerDto> orderBurgerDtoList = new ArrayList<>();


    @QueryProjection
    public AdminOrderDto(Long orderId, String orderDate, OrderStatus orderStatus, User user, OrderBurgerDto orderBurgerDto) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.user = user;
        this.orderBurgerDtoList = (List<OrderBurgerDto>) orderBurgerDto;
    }
}
