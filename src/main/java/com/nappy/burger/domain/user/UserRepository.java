package com.nappy.burger.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

    User findByNickname(String nickname);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
