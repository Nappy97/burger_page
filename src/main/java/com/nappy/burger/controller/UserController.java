package com.nappy.burger.controller;

import com.nappy.burger.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // 로그인
    @GetMapping("/auth/user/login")
    public String userLogin() {
        return "layout/user/user-login";
    }

    // 회원가입
    @GetMapping("/auth/user/save")
    public String userSave() {
        return "layout/user/user-save";
    }

    // 개인정보


    // 회원수정
    @GetMapping("/user/update")
    public String userUpdate(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        model.addAttribute("principal", principalDetail.getUser());
        return "layout/user/user-update";
    }
}
