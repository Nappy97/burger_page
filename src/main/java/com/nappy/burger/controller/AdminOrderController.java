package com.nappy.burger.controller;

import com.nappy.burger.config.auth.PrincipalDetail;
import com.nappy.burger.domain.order.Order;
import com.nappy.burger.dto.order.OrderSearchDto;
import com.nappy.burger.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminOrderController {

    private OrderService orderService;

    @GetMapping(value = {"/admin/orders", "/admin/orders/{page}"})
    public String orderManage(OrderSearchDto orderSearchDto,
                              @PathVariable(name = "page")Optional<Integer> page, Model model,
                              @AuthenticationPrincipal PrincipalDetail principalDetail){

        model.addAttribute("principal", principalDetail.getUser());
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Order> orders = orderService.getAdminOrderTotalPage(orderSearchDto, pageable);

        model.addAttribute("orders", orders);
        model.addAttribute("orderSearchDto", orderSearchDto);
        model.addAttribute("maxPage", 5);
        return "admin/orderMng";

    }
}
