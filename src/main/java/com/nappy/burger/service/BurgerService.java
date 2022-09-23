package com.nappy.burger.service;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.domain.burger.BurgerRepository;
import com.nappy.burger.dto.burger.BurgerSaveRequestDto;
import com.nappy.burger.dto.burger.BurgerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BurgerService {

    private final BurgerRepository burgerRepository;

    // 버거 생성
    @Transactional
    public Long save(BurgerSaveRequestDto burgerSaveRequestDto) {
        return burgerRepository.save(burgerSaveRequestDto.toEntity()).getId();
    }

    // 버거 목록
    @Transactional(readOnly = true)
    public Page<Burger> findByNameContainingOrContentContaining(String name, String content, Pageable pageable){
        return burgerRepository.findByNameContainingOrContentContaining(name, content, pageable);
    }

    // detail
    @Transactional(readOnly = true)
    public Burger detail(Long id){
        return burgerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 정보가 없습니다 id=" + id));
    }

    // 버거 삭제
    @Transactional
    public void deleteById(Long id){
        burgerRepository.deleteById(id);
    }

    // 버거 정보 수정
    @Transactional
    public Long update(Long id, BurgerUpdateRequestDto burgerUpdateRequestDto){
        Burger burger = burgerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id가 없습니다. id=" + id));
        burger.update(burgerUpdateRequestDto.getName(), burgerUpdateRequestDto.getContent(), burgerUpdateRequestDto.getExplanation(),
                burgerUpdateRequestDto.getType(), burgerUpdateRequestDto.getPrice());
        return id;
    }


}
