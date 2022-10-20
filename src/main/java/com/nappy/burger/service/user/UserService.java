package com.nappy.burger.service.user;

import com.nappy.burger.config.auth.PrincipalDetail;
import com.nappy.burger.domain.user.User;
import com.nappy.burger.dto.user.UserDto;
import com.nappy.burger.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입 로직
    public Long userSave(UserDto.RequestUserDto dto) {
        dto.encryptPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        User user = dto.toEntity();
        userRepository.save(user);
        log.info("회원 저장OK");

        return user.getId();
    }

    /* 아이디, 닉네임, 이메일 중복 여부 확인 */
    @Transactional(readOnly = true)
    public boolean idCheck(String username) throws Exception {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public String nickCheck(String nickname) throws Exception {
        boolean nicknameDuplicate = userRepository.existsByNickname(nickname);
        if (nicknameDuplicate) {
            return "false";
        } else {
            return "true";
        }
    }


    @Transactional(readOnly = true)
    public String nickCheck2(Long id, String nickname) throws Exception {
        User u = userRepository.searchById(id);
        String nickname1 = u.getNickname();
        if (Objects.equals(nickname1, nickname)) {
            return "true";
        } else {
            return "false";
        }
    }

    @Transactional(readOnly = true)
    public String emailCheck(String email) {
        boolean emailDuplicate = userRepository.existsByEmail(email);
        if (emailDuplicate) {
            return "false";
        } else {
            return "true";
        }
    }

    @Transactional(readOnly = true)
    public String emailCheck2(Long id, String email) throws Exception {
        User u = userRepository.searchById(id);
        String email1 = u.getEmail();
        if (Objects.equals(email1, email)) {
            return "true";
        } else {
            return "false";
        }
    }


    @Transactional
    public Long update(User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + user.getId()));
        userEntity.update(bCryptPasswordEncoder.encode(user.getPassword()), user.getNickname(),
                user.getName(), user.getZipcode(), user.getAddress(), user.getDetailAddress(),
                user.getEmail());
        principalDetail.setUser(userEntity);
        return userEntity.getId();
    }

    @Transactional
    public Long update1(User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + user.getId()));
        userEntity.update1(user.getNickname(), user.getName(), user.getZipcode(),
                user.getAddress(), user.getDetailAddress());
        principalDetail.setUser(userEntity);
        return userEntity.getId();
    }
}
