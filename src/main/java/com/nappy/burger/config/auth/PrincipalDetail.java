package com.nappy.burger.config.auth;

import com.nappy.burger.domain.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetail implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    //일반 사용자
    public PrincipalDetail(User user) {
        this.user = user;
    }

    //OAuth 사용자
    public PrincipalDetail(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> user.getRoleKey());

        return collection;
    }

    //사용자 패스워드
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //사용자 아이디
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //사용자 이메일
    public String getEmail() {
        return user.getEmail();
    }

    //사용자 닉네임
    public String getNickname() {
        return user.getNickname();
    }

    //사용자 pk
    public Long getId() {
        return user.getId();
    }

    // 사용자 이름
    public String getName(){
        return user.getName();
    }

    // 사용자 zipcode
    public int getZipcode(){
        return user.getZipcode();
    }

    // 사용자 address & detail
    public String getAddress(){
        return user.getAddress();
    }

    public String getDetailAddress(){
        return user.getDetailAddress();
    }

    //계정이 만료되었는지 (true: 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 (true: 잠겨있지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //패스워드가 만료되지 않았는지 (true: 만료되지 않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화되어 있는지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
