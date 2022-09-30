package com.nappy.burger.controller;

import com.nappy.burger.config.auth.PrincipalDetail;
import com.nappy.burger.domain.user.User;
import com.nappy.burger.dto.user.JoinFormDto;
import com.nappy.burger.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @GetMapping("/auth/user/save")
    public String userJoinForm() {
        return "user/joinForm";
    }

    @PostMapping("/auth/api/v1/user")
    public Long save(@RequestBody JoinFormDto joinFormDto) {
        return userService.saveUser(joinFormDto.toEntity());
    }

    // 로그인
    @GetMapping("/auth/user/login")
    public String userLogin() {
        return "user/loginForm";
    }

    // 로그인 실패
    @GetMapping("/auth/user/login/fail")
    public String userLoginFail(Model model){
        model.addAttribute("loginFailMsg", "아이디 혹은 비밀번호를 확인해주세요");
        return "user/loginForm";
    }


    // 회원 수정
    @GetMapping("/user/update")
    public String userUpdate(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        model.addAttribute("principal", principalDetail.getUser());
        return "user/updateForm";
    }

    @PutMapping("/api/v1/user")
    public Long update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        userService.updateUser(user, principalDetail);
        return user.getId();
    }
}
