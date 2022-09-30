package com.nappy.burger.controller;

import com.nappy.burger.domain.user.User;
import com.nappy.burger.dto.user.JoinFormDto;
import com.nappy.burger.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    // 회원가입
    @GetMapping("/auth/user/save")
    public String userJoinForm(JoinFormDto joinFormDto, Model model) {
        model.addAttribute("joinFormDto", joinFormDto);
        return "user/joinForm";
    }

    @PostMapping("/auth/user/save")
    public String userJoin(@Valid JoinFormDto joinFormDto, @NotNull BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            return "user/joinForm";
        }

        try{
            User user = User.createUser(joinFormDto, passwordEncoder);
            userService.saveUser(user);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "user/joinForm";
        }
        return "redirect:/";
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



//    // 개인정보
//
//
//    // 회원수정
//    @GetMapping("/user/update")
//    public String userUpdate(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
//        model.addAttribute("principal", principalDetail.getUser());
//        return "layout/user/user-update";
//    }
//    // 회원수정
//    @PutMapping("/api/v1/user")
//    public Long update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
//        userService.update(user, principalDetail);
//        return user.getId();
//    }
}
