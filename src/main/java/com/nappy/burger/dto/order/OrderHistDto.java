package com.nappy.burger.dto.order;

import com.nappy.burger.domain.order.Order;
import com.nappy.burger.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    private Long orderId;   // 주문 취소시에 필요
    private String orderDate;
    private OrderStatus orderStatus;
    private List<OrderBurgerDto> orderBurgerDtoList = new ArrayList<>();

    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    public void addOrderBurgerDto(OrderBurgerDto orderBurgerDto) {
        orderBurgerDtoList.add(orderBurgerDto);
    }
}
