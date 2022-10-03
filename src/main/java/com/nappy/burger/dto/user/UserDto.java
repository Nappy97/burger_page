package com.nappy.burger.dto.user;

import com.nappy.burger.domain.user.Role;
import com.nappy.burger.domain.user.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @Setter
    public static class RequestUserDto {

        private Long id;
        @NotBlank(message = "아이디는 필수입력입니다")
        @Length(min = 4, max = 16, message = "아이디는 4자 이상 16자 이하로 입력해주세요")
        @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
        private String username;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
        private String password;

        @NotBlank(message = "이메일은 필수 입력 값입니다")
        @Email(message = "이메일 형식으로 입력해주세요")
        private String email;

        @NotBlank(message = "닉네임은 필수 입력 값입니다")
        @Length(min = 2, message = "닉네임은 2자 이상 입력해주세요")
        private String nickname;

        private int zipcode;

        private String name;

        private String address;

        private String detailAddress;

        private Role role;

        public void encryptPassword(String BCryptpassword){
            this.password =BCryptpassword;
        }

        public User toEntity() {
            User user = User.builder()
                    .id(id)
                    .username(username)
                    .password(password)
                    .email(email)
                    .nickname(nickname)
                    .zipcode(zipcode)
                    .address(address)
                    .detailAddress(detailAddress)
                    .name(name)
                    .role(Role.USER)
                    .build();
            return user;
        }
    }
}