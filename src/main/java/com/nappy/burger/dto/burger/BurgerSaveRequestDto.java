package com.nappy.burger.dto.burger;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.domain.burger.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BurgerSaveRequestDto {

    private String name;
    private String type;
    private String content;
    private String explanation;
    private int price;
    private Set set;

    public Burger toEntity(){
        return Burger.builder()
                .name(name)
                .type(type)
                .content(content)
                .explanation(explanation)
                .price(price)
                .set(set)
                .build();
    }
}
