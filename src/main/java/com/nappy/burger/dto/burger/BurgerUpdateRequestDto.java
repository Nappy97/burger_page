package com.nappy.burger.dto.burger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BurgerUpdateRequestDto {

    private String name;
    private String content;
    private String type;
    private String explanation;
    private int price;
}
