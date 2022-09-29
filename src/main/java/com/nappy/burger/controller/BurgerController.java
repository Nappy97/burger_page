package com.nappy.burger.controller;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.dto.burger.BurgerFormDto;
import com.nappy.burger.dto.burger.BurgerSearchDto;
import com.nappy.burger.service.burger.BurgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BurgerController {

    private final BurgerService burgerService;

    // 버거 등록 페이지
    @GetMapping("/admin/burger/new")
    public String burgerForm(BurgerFormDto burgerFormDto, Model model) {

        model.addAttribute("burgerFormDto", burgerFormDto);
        return "burger/burgerForm";
    }

    // 등록
    @PostMapping(value = "/admin/burger/new")
    public String burgerNew(@Valid BurgerFormDto burgerFormDto, BindingResult bindingResult, Model model,
                            @RequestParam(name = "burgerImgFile") List<MultipartFile> burgerImgFileList) {
        if (bindingResult.hasErrors()) {
            return "burger/burgerForm";
        }

        if (burgerImgFileList.get(0).isEmpty() && burgerFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 이미지는 필수!");
            return "burger/burgerForm";
        }
        return "redirect:/";
    }

    // 관리 페이지
    @GetMapping(value = {"/admin/burgers", "/admin/burgers/{page}"})
    public String burgerManage(BurgerSearchDto burgerSearchDto,
                               @PathVariable(name = "page") Optional<Integer> page, Model model) {
        // PageRequest.of() 메소드를 통해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Burger> burgers = burgerService.getAdminBrugerPage(burgerSearchDto, pageable);

        model.addAttribute("burgers", burgers);
        model.addAttribute("burgerSearchDto", burgerSearchDto);
        model.addAttribute("maxPage", 5);
        return "burger/burgerMng";
    }

    // 수정 페이지
    @GetMapping(value = "/admin/burger/{burgerId}")
    public String burgerDetail(@PathVariable(name = "burgerId") Long burgerId, Model model) {
        try{
            BurgerFormDto burgerFormDto = burgerService.getBurgerDetail(burgerId);
            model.addAttribute("burgerFormDto", burgerFormDto);
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다!");
            model.addAttribute("burgerFormDto", new BurgerFormDto());
        }

        return "burger/burgerForm";
    }

    // 수정
    @PostMapping(value = "/admin/burger/{burgerId}")
    public String burgerUpdate(@Valid BurgerFormDto burgerFormDto, BindingResult bindingResult, Model model,
                               @RequestParam(name = "burgerImgFile") List<MultipartFile> burgerImgFileList){
        if (bindingResult.hasErrors()){
            return "burger/burgerForm";
        }

        if (burgerImgFileList.get(0).isEmpty() && burgerFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수!");
            return "burger/burgerForm";
        }

        return "redirect:/";
    }

    // 디테일 페이지
    @GetMapping(value = "/burgert/{burgerId}")
    public String burgerDetail(Model model, @PathVariable("burgerId") Long burgerId){
        BurgerFormDto burgerFormDto = burgerService.getBurgerDetail(burgerId);
        model.addAttribute("burger", burgerFormDto);
        return "burger/burgerDetail";
    }
}