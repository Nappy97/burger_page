package com.nappy.burger.domain.order;

import com.nappy.burger.domain.BaseEntity;
import com.nappy.burger.domain.burger.Burger;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_burger")
@Getter
@Setter
public class OrderBurger extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_burger_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "burger_id")
    private Burger burger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;  // 수량

    public static OrderBurger createOrderBurger(Burger burger, int count) {
        OrderBurger orderBurger = new OrderBurger();
        orderBurger.setBurger(burger);
        orderBurger.setCount(count);
        orderBurger.setOrderPrice(burger.getPrice());

        burger.removeStock(count);
        return orderBurger;
    }

    public int getTotalPrice() {
        return this.orderPrice * this.count;
    }

    public void cancel() {
        this.getBurger().addStock(count);
    }

}
