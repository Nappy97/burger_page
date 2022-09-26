package com.nappy.burger.dto.order;

import com.nappy.burger.domain.order.Order;
import com.nappy.burger.domain.burger.Set;
import com.nappy.burger.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class OrderSaveRequestDto {

    private int totalPrice;
    private User user;
    private String userAdd;
    private Set set;

    public Order toEntity(){
        return Order.builder()
                .totalPrice(totalPrice)
                .user(user)
                .userAdd(userAdd)
                .build();
    }

    public void setUser(User user){
        this.user = user;
    }

}
