package com.nappy.burger.dto.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBurgerTypeHistDto {

    private Integer count;

    private String burgerType;


    @QueryProjection
    public OrderBurgerTypeHistDto(String burgerType, int count){
        this.burgerType = burgerType;
        this.count = count;
    }
}
