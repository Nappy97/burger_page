package com.nappy.burger.controller;

import com.nappy.burger.config.auth.PrincipalDetail;
import com.nappy.burger.dto.burger.BurgerFormDto;
import com.nappy.burger.dto.order.OrderDto;
import com.nappy.burger.dto.order.OrderHistDto;
import com.nappy.burger.service.burger.BurgerService;
import com.nappy.burger.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final BurgerService burgerService;

    // 주문 체크]
    @GetMapping("orderCheck/{burgerId}")
    public String orderCheck(Model model, @PathVariable("burgerId") Long burgerId,
                             @AuthenticationPrincipal PrincipalDetail principalDetail){
        BurgerFormDto burgerFormDto = burgerService.getBurgerDetail(burgerId);
        model.addAttribute("principal", principalDetail.getUser());
        model.addAttribute("burger", burgerFormDto);
        return "order/orderCheck";
    }


    // 단일 상품 주문
    @PostMapping(value = "/order")
    @ResponseBody
    public ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
                                BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        Long orderId;
        try {
            orderId = orderService.order(orderDto, principal.getName());
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }




    // 주문 내역 조회
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable(name = "page") Optional<Integer> page,
                            Principal principal, Model model,
                            @AuthenticationPrincipal PrincipalDetail principalDetail) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        Page<OrderHistDto> orderHistDtos = orderService.getOrderList(principal.getName(), pageable);
        model.addAttribute("orders", orderHistDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        model.addAttribute("principal", principalDetail.getUser());
        return "order/orderHist";
    }

    // 주문 취소
    @PostMapping(value = "/order/{orderId}/cancel")
    @ResponseBody
    public ResponseEntity orderCancel(@PathVariable(name = "orderId") Long orderId, Principal principal) {

        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        orderService.orderCancel(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
