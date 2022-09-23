package com.nappy.burger.domain.burger;

import com.nappy.burger.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomBurger extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String type;

    @Column(nullable = false, length = 100)
    private String bread;

    @Column(nullable = false, length = 100)
    private String source;

    @Column(nullable = false, length = 200)
    private String patty;

    @Column(nullable = false, length = 200)
    private String additional;

    @Lob
    private String explanation;

    @Column(nullable = false, length = 200)
    private int price;

    // 수정
    public void update(String name, String explanation, int price, String type, String bread, String source, String patty, String additional) {
        this.name = name;
        this.explanation = explanation;
        this.price = price;
        this.type = type;
        this.bread = bread;
        this.source = source;
        this.patty = patty;
        this.additional = additional;
    }
}