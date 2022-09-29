package com.nappy.burger.dto.burger;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainBurgerDto {

    private Long id;
    private String burgerName;
    private String burgerExplanation;
    private String imgUrl;
    private Integer price;

    @QueryProjection
    public MainBurgerDto(Long id, String burgerName, String burgerExplanation,
                         String imgUrl, Integer price) {
        this.id = id;
        this.burgerName = burgerName;
        this.burgerExplanation = burgerExplanation;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
