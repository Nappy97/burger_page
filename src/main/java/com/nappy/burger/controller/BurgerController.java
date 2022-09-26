package com.nappy.burger.controller;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.service.BurgerService;
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
public class BurgerController {
    // TODO 관리자만 사용가능, 리스트보기
    private final BurgerService burgerService;

    // 버거 목록
    @GetMapping("/burger/list")
    public String index(Model model,
                        @PageableDefault(size = 20 , sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(required = false, defaultValue = "") String search) {
        Page<Burger> burgers = burgerService.findByNameContainingOrContentContaining(search, search, pageable);
        int startPage = Math.max(1, burgers.getPageable().getPageNumber() - 9); // 몇개 들어가야할까
        int endPage = Math.min(burgers.getTotalPages(), burgers.getPageable().getPageNumber() + 9);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("burgers", burgers);
        return "layout/burger/list";
    }

    // 버거 추가
    @GetMapping("/burger/save")
    public String save() {
        return "layout/burger/burger-save";
    }

    // 버거 상세
    @GetMapping("/burger/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("burger", burgerService.detail(id));
        return "layout/burger/burger-detail";
    }

    // 버거 수정
    @GetMapping("/burger/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("burger", burgerService.detail(id));
        return "layout/burger/burger-update";
    }

}
