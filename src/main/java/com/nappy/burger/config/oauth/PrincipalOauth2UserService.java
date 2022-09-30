package com.nappy.burger.config.oauth;

import com.nappy.burger.config.oauth.provider.GoogleUserInfo;
import com.nappy.burger.config.oauth.provider.KakaoUserInfo;
import com.nappy.burger.config.oauth.provider.NaverUserInfo;
import com.nappy.burger.config.oauth.provider.OAuth2UserInfo;
import com.nappy.burger.domain.user.User;
import com.nappy.burger.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = null;


        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }

        Optional<User> userOptional = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        User user;

//        if (userOptional.isPresent()) {
//            user = userOptional.get();
//            user.setEmail(oAuth2UserInfo.getEmail());
//            userRepository.save(user);
//        } else {
//            user = User.builder()
//                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
//                    .password(UUID.randomUUID().toString())
//                    .email(oAuth2UserInfo.getEmail())
//                    .nickname(oAuth2UserInfo.getNickname())
//                    .role(Role.USER)
//                    .provider(oAuth2UserInfo.getProvider())
//                    .providerId(oAuth2UserInfo.getProviderId())
//                    .build();
//
//            userRepository.save(user);
//        }
//
//        return new PrincipalDetail(user, oAuth2User.getAttributes());
//
    return null;
    }
}
