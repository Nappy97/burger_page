package com.nappy.burger.controller.api;

import com.nappy.burger.dto.burger.BurgerSaveRequestDto;
import com.nappy.burger.dto.burger.BurgerUpdateRequestDto;
import com.nappy.burger.service.BurgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BurgerApiController {

    private final BurgerService burgerService;

    // 버거 생성 API
    @PostMapping("/api/v1/burger")
    public Long save(@RequestBody BurgerSaveRequestDto burgerSaveRequestDto){
        return burgerService.save(burgerSaveRequestDto);
    }

    // 버거 삭제 API
    @DeleteMapping("/api/v1/burger/{id}")
    public Long deleteById(@PathVariable Long id){
        burgerService.deleteById(id);
        return id;
    }

    // 버거 수정 API
    @PutMapping("/api/v1/burger/{id}")
    public Long update(@PathVariable Long id, @RequestBody BurgerUpdateRequestDto burgerUpdateRequestDto){
        return burgerService.update(id, burgerUpdateRequestDto);
    }
}
