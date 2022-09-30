package com.nappy.burger.service.user;

import com.nappy.burger.domain.user.User;
import com.nappy.burger.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    // 회원가입 로직
    public User saveUser(User user){
        validateDuplicateUser(user);
        return userRepository.save(user);
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

    // 로그인 요청 검증을 위한 User 객체
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException(username);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoleKey())
                .build();
    }
//    // 회원수정
//    @Transactional
//    public Long update(User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
//        User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + user.getId()));
//        userEntity.update(bCryptPasswordEncoder.encode(user.getPassword()), user.getNickname());
//        principalDetail.setUser(userEntity); //시큐리티 세션 정보 변경
//        return userEntity.getId();
//    }
}
