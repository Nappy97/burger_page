package com.nappy.burger.controller;

import com.nappy.burger.config.auth.PrincipalDetail;
import com.nappy.burger.dto.order.AdminOrderHistDto;
import com.nappy.burger.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
@Log
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping({"/admin/ordersChk", "/admin/ordersChk/{page}"})
    public String adminOrderChk(@PathVariable(name = "page") Optional<Integer> page,
                                Model model,
                                @AuthenticationPrincipal PrincipalDetail principalDetail) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        Page<AdminOrderHistDto> orders = orderService.getAdminOrderList(pageable);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        model.addAttribute("principal", principalDetail.getUser());

        return "admin/orderChk";
    }

//    @GetMapping(value = {"/admin/orders", "/admin/orders/{page}"})
//    public String orderManage(OrderSearchDto orderSearchDto,
//                              @PathVariable(name = "page") Optional<Integer> page, Model model,
//                              @AuthenticationPrincipal PrincipalDetail principalDetail) {
//
//        model.addAttribute("principal", principalDetail.getUser());
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
//        log.info(orderSearchDto.getSearchQuery());
//        log.info(orderSearchDto.getSearchBy());
//        log.info(orderSearchDto.getSearchDateType());
//
//        Page<Order> orders = orderService.getAdminOrderTotalPage(orderSearchDto, pageable);
//
//        model.addAttribute("orders", orders);
//        model.addAttribute("orderSearchDto", orderSearchDto);
//        model.addAttribute("maxPage", 5);
//        return "admin/orderMng";
//    }
}
