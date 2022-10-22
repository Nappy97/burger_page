package com.nappy.burger.service.cart;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.domain.cart.Cart;
import com.nappy.burger.domain.cart.CartBurger;
import com.nappy.burger.domain.user.User;
import com.nappy.burger.dto.cart.CartBurgerDto;
import com.nappy.burger.dto.cart.CartListDto;
import com.nappy.burger.dto.cart.CartOrderDto;
import com.nappy.burger.dto.order.OrderDto;
import com.nappy.burger.repository.burger.BurgerRepository;
import com.nappy.burger.repository.cart.CartBurgerRepository;
import com.nappy.burger.repository.cart.CartRepository;
import com.nappy.burger.repository.user.UserRepository;
import com.nappy.burger.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartBurgerRepository cartBurgerRepository;
    private final UserRepository userRepository;
    private final BurgerRepository burgerRepository;
    private final OrderService orderService;


    // 장바구니 담기
    public Long addCart(CartBurgerDto cartBurgerDto, String username) {

        User user = userRepository.findByUsername(username);
        Cart cart = cartRepository.findByUserId(user.getId());

        // 장바구니가 존재하지 않는 다면 생성
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        Burger burger = burgerRepository.findById(cartBurgerDto.getBurgerId()).orElseThrow(EntityExistsException::new);
        CartBurger cartBurger = cartBurgerRepository.findByCartIdAndBurgerId(cart.getId(), burger.getId());

        // 해당상품이 장바구니에 존재하지 않을 시에 생성 후 추가
        if (cartBurger == null) {
            cartBurger = CartBurger.createCartBurger(cart, burger, cartBurgerDto.getCount());
            cartBurgerRepository.save(cartBurger);

            // 해당 상품이 장바구니에 이미 존재한다면 수량을 증가
        } else {
            cartBurger.addCount(cartBurgerDto.getCount());
        }
        return cartBurger.getId();
    }

    // 장바구니 조회
    @Transactional(readOnly = true)
    public List<CartListDto> getCartList(String username) {

        List<CartListDto> cartListDtos = new ArrayList<>();

        User user = userRepository.findByUsername(username);
        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart == null) {
            return cartListDtos;
        }

        cartListDtos = cartBurgerRepository.findCartListDto(cart.getId());
        return cartListDtos;
    }

    // 장바구니 조회
    @Transactional(readOnly = true)
    public Cart getCart(String username) {

        List<CartListDto> cartListDtos = new ArrayList<>();

        User user = userRepository.findByUsername(username);

        return cartRepository.findByUserId(user.getId());
    }

    // 현재 로그인한 사용자가 장바구니의 주인인지 확인
    @Transactional(readOnly = true)
    public boolean validateCartBurger(Long cartBurgerId, String username) {

        // 현재 로그인된 사용자
        User curuser = userRepository.findByUsername(username);

        CartBurger cartBurger = cartBurgerRepository.findById(cartBurgerId).orElseThrow(EntityNotFoundException::new);
        User savedUser = cartBurger.getCart().getUser();

        if (StringUtils.equals(curuser.getUsername(), savedUser.getUsername())) {
            return true;
        }
        return false;
    }

    // 장바구니 상품 수량 변경
    public void updateCartBurgerCount(Long cartBurgerId, int count) {
        CartBurger cartBurger = cartBurgerRepository.findById(cartBurgerId).orElseThrow(EntityNotFoundException::new);
        cartBurger.updateCount(count);
    }

    // 장바구니 상품 삭제
    public void deleteCartBurger(Long cartBurgerId) {
        CartBurger cartBurger = cartBurgerRepository.findById(cartBurgerId).orElseThrow(EntityNotFoundException::new);
        cartBurgerRepository.delete(cartBurger);
    }

    // 장바구니 상품을 주문
    public Long orderCartBurger(List<CartOrderDto> cartOrderDtoList, String username) {

        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartBurger cartBurger = cartBurgerRepository.findById(cartOrderDto.getCartBurgerId()).orElseThrow(EntityExistsException::new);
            OrderDto orderDto = new OrderDto();
            orderDto.setBurgerId(cartBurger.getBurger().getId());
            orderDto.setCount(cartBurger.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, username);

        // 주문한 장바구니 상품을 제거
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartBurger cartBurger = cartBurgerRepository.findById(cartOrderDto.getCartBurgerId()).orElseThrow(EntityExistsException::new);
            cartBurgerRepository.delete(cartBurger);
        }
        return orderId;
    }
}
