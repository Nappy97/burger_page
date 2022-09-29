package com.nappy.burger.domain.burger;

import com.nappy.burger.domain.BaseEntity;
import com.nappy.burger.dto.burger.BurgerFormDto;
import com.nappy.burger.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Entity
@ToString
@Setter
public class Burger extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "burger_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String burgerName;

    @Column(nullable = false, length = 100)
    private String burgerType;

    @Lob
    private String burgerContent;

    @Lob
    private String burgerExplanation;

    @Column(nullable = false)
    private int stock;
    @Column
    private int price;

    @Enumerated(EnumType.STRING)
    private BurgerSellStatus burgerSellStatus;


    // 수정
    public void updateBurger(BurgerFormDto burgerFormDto) {
        this.burgerName = burgerFormDto.getBurgerName();
        this.burgerContent = burgerFormDto.getBurgerContent();
        this.burgerExplanation = burgerFormDto.getBurgerExplanation();
        this.price = burgerFormDto.getPrice();
        this.burgerType = burgerFormDto.getBurgerType();
        this.stock = burgerFormDto.getStock();
        this.burgerSellStatus = burgerFormDto.getBurgerSellStatus();
    }

    // 재고 부족일 경우
    public void removeStock(int stock){
        int restStock = this.stock - stock;
        if (restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (남은 재고 : " + this.stock + ")");
        }
        this.stock = restStock;
    }

    public void addStock(int stock){
        this.stock += stock;
    }

}
