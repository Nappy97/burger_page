package com.nappy.burger.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username; //아이디

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;    // 이름

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50, unique = true)
    private String phone;

    @Column(nullable = false, length = 10)
    private int zipcode;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 50)
    private String nickname; //닉네임

    @Column(nullable = false)
    private Date birth;

    @Column(nullable = false, length = 100)
    private String gender;

    @Column(length = 300)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
