package com.nappy.burger.dto.user;

import com.nappy.burger.domain.user.Role;
import com.nappy.burger.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UserSaveRequestDto {



    @NotBlank(message = "아이디는 필수입력입니다")
    private String username;
    private String password;
    private String email;
    private String nickname;
    private int zipcode;
    private String name;
    private String address;
    private String detailAddress;
    private Role role;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
//                .name(name)
//                .zipcode(zipcode)
//                .address(address)
//                .detailAddress(detailAddress)
                .role(Role.USER)
                .build();
    }

}
