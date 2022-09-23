package com.nappy.burger.domain.order;

import com.nappy.burger.domain.user.User;
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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //sequence, auto_increment

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false, length = 200)
    private String userAdd;

    @Column(nullable = false, length = 200)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set set;

    @Lob
    private String review;

    // 세트 여부
    public String getSetKey(){
        return this.set.getKey();
    }
}
