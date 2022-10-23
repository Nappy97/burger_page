package com.nappy.burger.service.order;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.domain.burger.BurgerImg;
import com.nappy.burger.domain.order.Order;
import com.nappy.burger.domain.order.OrderBurger;
import com.nappy.burger.domain.user.User;
import com.nappy.burger.dto.order.OrderBurgerDto;
import com.nappy.burger.dto.order.OrderDto;
import com.nappy.burger.dto.order.OrderHistDto;
import com.nappy.burger.dto.order.OrderSearchDto;
import com.nappy.burger.repository.burger.BurgerImgRepository;
import com.nappy.burger.repository.burger.BurgerRepository;
import com.nappy.burger.repository.order.OrderRepository;
import com.nappy.burger.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final BurgerRepository burgerRepository;        // 상품을 불러와서 재고를 변경
    private final UserRepository userRepository;    // 멤버를 불러와서 연결
    private final OrderRepository orderRepository;      // 주문 객체를 저장
    private final BurgerImgRepository burgerImgRepository;  // 상품 대표 이미즈를 출력

    // 단일 상품 주문
    public Long order(OrderDto orderDto, String username) {

        // OrderBurger(List) 생성
        List<OrderBurger> orderBurgerList = new ArrayList<>();
        Burger burger = burgerRepository.findById(orderDto.getBurgerId()).orElseThrow(EntityNotFoundException::new);
        orderBurgerList.add(OrderBurger.createOrderBurger(burger, orderDto.getCount()));

        // Order 객체 생성
        User user = userRepository.findByUsername(username);
        Order order = Order.createOrder(user, orderBurgerList);

        // Order 객체 DB 저장 (Cascade 로 인해 OrderItem 객체도 같이 저장)
        orderRepository.save(order);
        return order.getId();
    }

    // 주문 내역 조회
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String username, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(username, pageable);
        Long totalCount = orderRepository.countOrder(username);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderBurger> orderBurgers = order.getOrderBurgers();

            for (OrderBurger orderBurger : orderBurgers) {
                BurgerImg burgerImg = burgerImgRepository.findByBurgerIdAndRepimgYn(orderBurger.getBurger().getId(), "Y");
                OrderBurgerDto orderBurgerDto = new OrderBurgerDto(orderBurger, burgerImg.getImgUrl());
                orderHistDto.addOrderBurgerDto(orderBurgerDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<>(orderHistDtos, pageable, totalCount);
    }

    // 주문한 유저가 맞는지 확인]
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String username) {

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        if (StringUtils.equals(order.getUser().getUsername(), username)) {
            return true;
        }
        return false;
    }

    // 주문 취소
    public void orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.orderCancel();
    }

    // 장바구니 상품을 주문
    public Long orders(List<OrderDto> orderDtoList, String username) {

        // 로그인한 유저조회
        User user = userRepository.findByUsername(username);

        // orderDto 객체를 이용하여 burger 객체와 count 값을 얻은 뒤, 이를 이용하여 OrderBurger 객체 생성
        List<OrderBurger> orderBurgerList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Burger burger = burgerRepository.findById(orderDto.getBurgerId()).orElseThrow(EntityNotFoundException::new);
            OrderBurger orderBurger = OrderBurger.createOrderBurger(burger, orderDto.getCount());
            orderBurgerList.add(orderBurger);
        }

        // Order Entity 클래스에 존재하는 createOrder 메소드로 Order 생성 및 저장
        Order order = Order.createOrder(user, orderBurgerList);
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<Order> getAdminOrderTotalPage(OrderSearchDto orderSearchDto, Pageable pageable){
        return orderRepository.getAdminOrderTotalPage(orderSearchDto, pageable);
    }
}
































