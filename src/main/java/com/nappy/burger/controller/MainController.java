package com.nappy.burger.controller;

import com.nappy.burger.dto.burger.BurgerSearchDto;
import com.nappy.burger.dto.burger.MainBurgerDto;
import com.nappy.burger.service.burger.BurgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final BurgerService burgerService;

    @GetMapping(value = "/")
    public String main(BurgerSearchDto burgerSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainBurgerDto> burgers = burgerService.getMainBurgerPage(burgerSearchDto, pageable);

        model.addAttribute("burgers", burgers);
        model.addAttribute("burgerSearchDto", burgerSearchDto);
        model.addAttribute("maxPage", 5);
        return "main";
    }
}
