package com.nappy.burger.dto.order;

import com.nappy.burger.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchDto {

    private String searchDateType;
    private OrderStatus searchOrderStatus;
    private String searchBy;
    private String searchQuery = "";
}
