package com.nappy.burger.domain.cart;

import com.nappy.burger.domain.BaseEntity;
import com.nappy.burger.domain.burger.Burger;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartBurger extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_burger_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "burger_id")
    private Burger burger;

    private int count;

    public static CartBurger createCartBurger(Cart cart, Burger burger, int count) {
        CartBurger cartBurger = new CartBurger();
        cartBurger.setCart(cart);
        cartBurger.setBurger(burger);
        cartBurger.setCount(count);
        return cartBurger;
    }
    public void addCount(int count){
        this.count += count;
    }

    public void updateCount(int count){
        this.count = count;
    }
}
