package com.nappy.burger.dto.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBurgerHistDto {

    private Long burgerId;

    private Integer count;

    private String burgerName;

    private String imgUrl;

    private Integer price;

    @QueryProjection
    public OrderBurgerHistDto(Long id, int count, String imgUrl, Integer price, String burgerName){
        this.burgerId = id;
        this.count = count;
        this.imgUrl = imgUrl;
        this.price = price;
        this.burgerName = burgerName;
    }
}
