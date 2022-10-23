package com.nappy.burger.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchDto {

    private String searchDateType;
    private String searchBy;
    private String searchQuery = "";
}
