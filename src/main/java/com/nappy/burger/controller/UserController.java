package com.nappy.burger.controller;

import com.nappy.burger.config.auth.PrincipalDetail;
import com.nappy.burger.domain.user.User;
import com.nappy.burger.dto.user.UserDto;
import com.nappy.burger.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    // 회원가입
    @GetMapping("/auth/user/save")
    public String userJoinForm() {
        return "user/joinForm";
    }

    //    @PostMapping("/auth/api/v1/user")
//    public Long save(@RequestBody JoinFormDto joinFormDto) {
//        return userService.saveUser(joinFormDto.toEntity());
//    }
    @PostMapping("/auth/user/saveProc")
    public String saveProc(@Valid UserDto.RequestUserDto userDto, BindingResult bindingResult, Model model) {

        /* 검증 */
        if (bindingResult.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 값 유지 */
            model.addAttribute("userDto", userDto);

            /* 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링 */
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
                log.info("회원가입 실패 ! error message : " + error.getDefaultMessage());
            }

            /* Model에 담아 view resolve */
            for (String key : errorMap.keySet()) {
                model.addAttribute(key, errorMap.get(key));
            }

            /* 회원가입 페이지로 리턴 */
            return "/user/joinForm";
        }

        // 회원가입 성공 시
        userService.userSave(userDto);
        log.info("회원가입 성공");
        return "redirect:/auth/user/login";
    }

    // 아이디 중복체크
    @RequestMapping(value = "/auth/user/saveProc/idCheck", method = RequestMethod.POST)
    @ResponseBody
    public String userIdChkPOST(String username) throws Exception {
        boolean checkId = userService.idCheck(username);
        if (checkId) {
            return "false";
        } else {
            return "true";
        }
    }

    // 닉네임 중복체크
    @RequestMapping(value = "/auth/user/saveProc/nicknameCheck", method = RequestMethod.POST)
    @ResponseBody
    public String userNickChkPOST(String nickname) throws Exception {
        return userService.nickCheck(nickname);
    }

    @RequestMapping(value = "/auth/user/modifyProc/nicknameCheck", method = RequestMethod.POST)
    @ResponseBody
    public String userNickCheckPost(String nickname, Long id) throws Exception{
        return userService.nickCheck2(id, nickname);
    }

    // 이메일 중복체크
    @RequestMapping(value = "/auth/user/saveProc/emailCheck", method = RequestMethod.POST)
    @ResponseBody
    public String userEmailChkPOST(String email) throws Exception {
        return userService.emailCheck(email);
    }

    @RequestMapping(value = "/auth/user/modifyProc/emailCheck", method = RequestMethod.POST)
    @ResponseBody
    public String userEmailCheckPost(String email, Long id) throws Exception{
        return userService.emailCheck2(id, email);
    }

    // 로그인
    @GetMapping("/auth/user/login")
    public String userLogin() {
        return "user/loginForm";
    }

    // 로그인 실패
    @GetMapping("/auth/user/login/fail")
    public String userLoginFail(Model model) {
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
    @ResponseBody
    public Long update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        userService.update(user, principalDetail);
        return user.getId();
    }

    @GetMapping("/oauth/user/update")
    public String userUpdate1(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model){
        model.addAttribute("principal", principalDetail.getUser());
        return "user/oauthModify";
    }
    @PutMapping("/api/v1/oauthUser")
    @ResponseBody
    public Long update1(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail){
        userService.update1(user, principalDetail);
        return user.getId();
    }

}
