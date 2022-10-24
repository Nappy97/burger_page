package com.nappy.burger.domain.order;

import com.nappy.burger.domain.BaseEntity;
import com.nappy.burger.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id; //sequence, auto_increment

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // order_burger 테이블의 order 필들에 맾핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderBurger> orderBurgers = new ArrayList<>();

    public void addOrderBurger(OrderBurger orderBurger) {
        orderBurgers.add(orderBurger);
        orderBurger.setOrder(this);
    }

    public static Order createOrder(User user, List<OrderBurger> orderBurgerList) {
        Order order = new Order();
        order.setUser(user);
        for (OrderBurger orderBurger : orderBurgerList) {
            order.addOrderBurger(orderBurger);
        }
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ORDER);
        return order;
    }

    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderBurger orderBurger : orderBurgers) {
            totalPrice += orderBurger.getTotalPrice();
        }
        return totalPrice;
    }

    public void orderCancel() {
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderBurger orderBurger : orderBurgers) {
            orderBurger.cancel();
        }
    }

}
