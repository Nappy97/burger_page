package com.nappy.burger.dto.order;

import com.nappy.burger.domain.order.OrderBurger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class    OrderBurgerDto {

    private String burgerName;
    private int count;
    private int orderPrice;
    private String imgUrl;

    public OrderBurgerDto(OrderBurger orderBurger, String imgUrl){
        this.burgerName = orderBurger.getBurger().getBurgerName();
        this.count = orderBurger.getCount();
        this.orderPrice = orderBurger.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
