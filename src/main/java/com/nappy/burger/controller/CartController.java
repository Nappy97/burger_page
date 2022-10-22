package com.nappy.burger.controller;

import com.nappy.burger.config.auth.PrincipalDetail;
import com.nappy.burger.domain.cart.Cart;
import com.nappy.burger.dto.cart.CartBurgerDto;
import com.nappy.burger.dto.cart.CartListDto;
import com.nappy.burger.dto.cart.CartOrderDto;
import com.nappy.burger.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    // 장바구니 담기
    @PostMapping(value = "/cart")
    @ResponseBody
    public ResponseEntity cart(@RequestBody @Valid CartBurgerDto cartBurgerDto,
                               BindingResult bindingResult, Principal principal) {
        log.info("hi");
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        Long cartBurgerId;

        try {
            cartBurgerId = cartService.addCart(cartBurgerDto, principal.getName());
            log.info(cartBurgerId.toString());
        } catch (Exception e) {
            log.info("hi2");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        log.info("hi3");
        return new ResponseEntity<Long>(cartBurgerId, HttpStatus.OK);
    }

    // 장바구니 조회
    @GetMapping(value = "/cart")
    public String cartList(Principal principal, Model model,
                           @AuthenticationPrincipal PrincipalDetail principalDetail) {

        model.addAttribute("principal", principalDetail.getUser());
        List<CartListDto> cartListDtos = cartService.getCartList(principal.getName());
        model.addAttribute("cartBurgers", cartListDtos);
        log.info(cartListDtos.toString());
        return "cart/cartList";
    }

    // 장바구니 상품 수량 변경
    @PatchMapping(value = "/cartBurger/{cartBurgerId}")
    @ResponseBody
    public ResponseEntity updateCartBurger(@PathVariable(name = "cartBurgerId") Long cartBurgerId,
                                           int count, Principal principal) {

        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartBurger(cartBurgerId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.updateCartBurgerCount(cartBurgerId, count);
        return new ResponseEntity<Long>(cartBurgerId, HttpStatus.OK);
    }

    // 장바구니 삭제
    @DeleteMapping(value = "/cartBurger/{cartBurgerId}")
    @ResponseBody
    public ResponseEntity deleteCartBurger(@PathVariable(name = "cartBurgerId") Long cartBurgerId, Principal principal) {

        if (!cartService.validateCartBurger(cartBurgerId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartBurger(cartBurgerId);
        return new ResponseEntity<Long>(cartBurgerId, HttpStatus.OK);
    }

    // 장바구니 상품을 주문
    @GetMapping(value = "/cartChk")
    public String cartCheck(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail, Principal principal) {
        model.addAttribute("principal", principalDetail.getUser());
        List<CartListDto> cartListDtos = cartService.getCartList(principal.getName());
        model.addAttribute("cartBurgers", cartListDtos);
        Cart carts = cartService.getCart(principal.getName());
        model.addAttribute("cart", carts);
        return "cart/cartChk";
    }

    @GetMapping(value = "/payChk/{cartId}")
    public String payCheck(Model model, @PathVariable("cartId") Long cartId,
                           @AuthenticationPrincipal PrincipalDetail principalDetail,
                           Principal principal) {
        model.addAttribute("principal", principalDetail.getUser());
        List<CartListDto> cartListDtos = cartService.getCartList(principal.getName());
        model.addAttribute("cartBurgers", cartListDtos);
        return "cart/payChk";
    }

    @PostMapping(value = "/cart/orders")
    @ResponseBody
    public ResponseEntity orders(@RequestBody CartOrderDto cartOrderDto, Principal principal) {

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요!", HttpStatus.BAD_REQUEST);
        }

        // 장바구니 주문 상품들을 각각 검증
        for (CartOrderDto cartOrderDto1 : cartOrderDtoList) {
            if (!cartService.validateCartBurger(cartOrderDto1.getCartBurgerId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.orderCartBurger(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/ordersChk")
    @ResponseBody
    public ResponseEntity ordersChk(@RequestBody CartOrderDto cartOrderDto, Principal principal) {

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요!", HttpStatus.BAD_REQUEST);
        }

        // 장바구니 주문 상품들을 각각 검증
        for (CartOrderDto cartOrderDto1 : cartOrderDtoList) {
            if (!cartService.validateCartBurger(cartOrderDto1.getCartBurgerId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.orderCartBurger(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
