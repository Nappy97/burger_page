package com.nappy.burger.dto.user;

import com.nappy.burger.domain.user.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinFormDto {

    @NotBlank(message = "아이디는 필수입력입니다")
    @Length(min = 4, max = 16, message = "아이디는 4자 이상 16자 이하로 입력해주세요")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    @Length(min = 8, max = 16, message = "8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotBlank(message = "이메일은 필수 입력 값입니다")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 값입니다")
    private String nickname;

    @NotNull(message = "우편번호를 입력해주세요")
    private int zipcode;

    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String name;

    @NotBlank(message = "주소는 필수 입력 값입니다")
    private String address;

    @NotBlank(message = "상세주소는 필수 입력 값입니다")
    private String detailAddress;


    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .zipcode(zipcode)
                .address(address)
                .detailAddress(detailAddress)
                .name(name)
                .build();
    }
}
