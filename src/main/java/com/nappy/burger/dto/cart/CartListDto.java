package com.nappy.burger.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListDto {

    private Long cartBurgerId;
    private String burgerName;
    private int price;
    private int count;
    private String imgUrl;

    public CartListDto(Long cartBurgerId, String burgerName, int price, int count, String imgUrl) {
        this.cartBurgerId = cartBurgerId;
        this.burgerName = burgerName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
