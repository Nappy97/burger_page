package com.nappy.burger.repository.order;

import com.nappy.burger.domain.order.Order;
import com.nappy.burger.dto.order.OrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<Order> getAdminOrderTotalPage(OrderSearchDto orderSearchDto, Pageable pageable);
}
