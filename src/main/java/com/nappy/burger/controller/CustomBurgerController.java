package com.nappy.burger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CustomBurgerController {
    // TODO 관리자만 사용가능
    private final CustomBurgerService customBurgerService;

    // 버거 목록
    @GetMapping("/customburger/list")
    public String index(Model model,
                        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                        @RequestParam(required = false, defaultValue = "") String search){
        Page<CustomBurger> customBurgers = customBurgerService.findByNameContainingOrPattyContaining(search, search, search, search,search, pageable);
        int startPage = Math.max(1, customBurgers.getPageable().getPageNumber() - 9); // 몇개 들어가야할까
        int endPage = Math.min(customBurgers.getTotalPages(), customBurgers.getPageable().getPageNumber() + 9);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("customBurgers", customBurgers);
        return "layout/customburger/list";
    }

    // 버거 추가
    @GetMapping("/customburger/save")
    public String save() {
        return "layout/customburger/customburger-save";
    }

    // 버거 상세
    @GetMapping("/customburger/{id}")
    public String detail(@PathVariable Long id, Model model){
        model.addAttribute("customburger", customBurgerService.detail(id));
        return "layout/customburger/customeburger-detail";
    }

    // 버거 수정
    @GetMapping("/customburger/{id}/update")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("burger", customBurgerService.detail(id));
        return "layout/customburger/customburger-update";
    }

}
