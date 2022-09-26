package com.nappy.burger.dto.burger;

import com.nappy.burger.domain.burger.CustomBurger;
import com.nappy.burger.domain.burger.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomBurgerSaveRequestDto {

    private String name;
    private String type;
    private String bread;
    private String patty;
    private String source;
    private String additional;
    private String explanation;
    private int price;
    private Set set;

    public CustomBurger toEntity(){
        return CustomBurger.builder()
                .name(name)
                .type(type)
                .explanation(explanation)
                .bread(bread)
                .patty(patty)
                .source(source)
                .additional(additional)
                .price(price)
                .set(set)
                .build();
    }
}
