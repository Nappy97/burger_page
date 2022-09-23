package com.nappy.burger.controller;

import com.nappy.burger.service.BurgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BurgerController {
    // 관리자만 사용가능
    private final BurgerService burgerService;

    // 버거 추가
    @GetMapping("/burger/save")
    public String save() {
        return "layout/burger/burger-save";
    }

    // 버거 상세
    @GetMapping("/burger/{id}")
    public String detail(@PathVariable Long id, Model model){
        model.addAttribute("burger", burgerService.detail(id));
        return "layout/burger/burger-detail";
    }

    // 버거 수정
    @GetMapping("/burger/{id}/update")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("burger", burgerService.detail(id));
        return "layout/burger/burger-update";
    }

}
