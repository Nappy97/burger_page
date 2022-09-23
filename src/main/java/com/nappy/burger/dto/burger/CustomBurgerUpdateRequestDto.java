package com.nappy.burger.dto.burger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomBurgerUpdateRequestDto {

    private String name;
    private String explanation;
    private int price;
    private String type;
    private String patty;
    private String additional;
    private String bread;
    private String source;
}
