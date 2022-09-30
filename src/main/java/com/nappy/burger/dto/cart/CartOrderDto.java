package com.nappy.burger.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartBurgerId;
    private List<CartOrderDto> cartOrderDtoList;
}
