package com.nappy.burger.validator;

import com.nappy.burger.dto.user.UserDto;
import com.nappy.burger.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<UserDto.RequestUserDto>{

    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserDto.RequestUserDto dto, Errors errors) {
        if (userRepository.existsByNickname(dto.toEntity().getNickname())) {
            /* 중복인 경우 */
            errors.rejectValue("nickname","닉네임 중복 오류", "이미 사용 중인 닉네임입니다.");
        }
    }


}