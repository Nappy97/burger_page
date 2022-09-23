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
public class Burger extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String type;

    @Lob
    private String content;

    @Lob
    private String explanation;

    @Column(nullable = false, length = 200)
    private int price;

    // 수정
    public void update(String name, String content, String explanation, String type, int price) {
        this.name = name;
        this.content = content;
        this.explanation = explanation;
        this.price = price;
        this.type = type;
    }
}