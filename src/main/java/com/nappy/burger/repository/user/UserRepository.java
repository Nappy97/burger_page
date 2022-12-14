package com.nappy.burger.repository.user;

import com.nappy.burger.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

    User findByNickname(String nickname);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    Optional<User> searchByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    User searchById(Long id);
}
