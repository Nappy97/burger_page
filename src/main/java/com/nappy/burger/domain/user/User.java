package com.nappy.burger.domain.user;

import com.nappy.burger.domain.BaseEntity;
import com.nappy.burger.dto.user.JoinFormDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //sequence, auto_increment

    @Column(nullable = false, length = 50, unique = true)
    private String username; //아이디

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 30, unique = true)
    private String email;

    @Column(length = 20, unique = true)
    private String nickname;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private int zipcode;

    @Column(length = 200)
    private String address;

    @Column(length = 200)
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String provider;

    @Column
    private String providerId;



//    // 비밀번호 암호화
//    public void setPassword(String password) {
//        this.password = password;
//    }

    /**
     * 권한 메소드
     */
    public String getRoleKey() {
        return this.role.getKey();
    }

    // 회원가입
    public static User createUser(JoinFormDto joinFormDto, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setUsername(joinFormDto.getUsername());
        String password = passwordEncoder.encode(joinFormDto.getPassword());
        user.setPassword(password);
        user.setEmail(joinFormDto.getEmail());
        user.setName(joinFormDto.getName());
        user.setNickname(joinFormDto.getNickname());
        user.setZipcode(joinFormDto.getZipcode());
        user.setAddress(joinFormDto.getAddress());
        user.setDetailAddress(joinFormDto.getDetailAddress());
        user.setRole(Role.USER);
        return user;
    }


//    // 회원수정 메소드
//    public User update(String password, String nickname
////                       ,String name, int zipcode, String address, String detailAddress, String email
//                       ) {
//        this.password = password;
//        this.nickname = nickname;
////        this.name = name;
////        this.zipcode = zipcode;
////        this.address = address;
////        this.detailAddress = detailAddress;
////        this.email = email;
//        return this;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }


}

