package com.nappy.burger.service;

import com.nappy.burger.config.auth.PrincipalDetail;
import com.nappy.burger.domain.user.User;
import com.nappy.burger.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입 로직
    @Transactional
    public Long save(User user){
        String hashPw = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPw);
        validateDuplicateUser(user);
        return userRepository.save(user).getId();
    }

    // 중복회원 검증
    public void validateDuplicateUser(User user) {
        User emailChk = userRepository.findByEmail(user.getEmail());
        User usernameChk = userRepository.findByUsername(user.getUsername());
        User nicknameChk = userRepository.findByNickname(user.getNickname());

        if (usernameChk != null) {
            throw new IllegalStateException("이미 가입된 회원입니다");
        } else if (emailChk != null) {
            throw  new IllegalStateException("중복된 이메일입니다");
        } else if (nicknameChk != null) {
            throw new IllegalStateException("중복된 닉네임입니다");
        }
    }

    // 회원수정
    @Transactional
    public Long update(User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + user.getId()));
        userEntity.update(bCryptPasswordEncoder.encode(user.getPassword()), user.getNickname());
        principalDetail.setUser(userEntity); //시큐리티 세션 정보 변경
        return userEntity.getId();
    }
}
