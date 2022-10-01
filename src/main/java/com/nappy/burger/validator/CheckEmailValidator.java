package com.nappy.burger.validator;

import com.nappy.burger.dto.user.UserDto;
import com.nappy.burger.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<UserDto.RequestUserDto>{

    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserDto.RequestUserDto dto, Errors errors) {
        if (userRepository.existsByEmail(dto.toEntity().getEmail())) {
            /* 중복인 경우 */
            errors.rejectValue("email","이메일 중복 오류", "이미 사용 중인 이메일입니다.");
        }
    }
}