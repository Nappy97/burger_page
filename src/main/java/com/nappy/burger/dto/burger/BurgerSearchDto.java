package com.nappy.burger.dto.burger;

import com.nappy.burger.domain.burger.BurgerSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BurgerSearchDto {

    private String searchDateType;
    private BurgerSellStatus searchSellStatus;
    private String searchBy;
    private String searchQuery = "";
}
