package com.nappy.burger.service;

import com.nappy.burger.domain.burger.CustomBurger;
import com.nappy.burger.domain.burger.CustomBurgerRepository;
import com.nappy.burger.dto.burger.CustomBurgerSaveRequestDto;
import com.nappy.burger.dto.burger.CustomBurgerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomBurgerService {

    private final CustomBurgerRepository customBurgerRepository;

    // 버거 생성
    @Transactional
    public Long save(CustomBurgerSaveRequestDto customBurgerSaveRequestDto) {
        return customBurgerRepository.save(customBurgerSaveRequestDto.toEntity()).getId();
    }

    // 버거 목록
    @Transactional(readOnly = true)
    Page<CustomBurger> findByNameContainingOrPattyContaining(String name, String patty, String bread, String source, String additional, Pageable pageable) {
        return customBurgerRepository.findByNameContainingOrPattyContaining(name, patty, bread, source, additional, pageable);
    }

    // detail
    @Transactional(readOnly = true)
    public CustomBurger detail(Long id) {
        return customBurgerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 정보가 없습니다 id=" + id));
    }

    // 버거 삭제
    @Transactional
    public void deleteById(Long id) {
        customBurgerRepository.deleteById(id);
    }

    // 버거 정보 수정
    @Transactional
    public Long update(Long id, CustomBurgerUpdateRequestDto customBurgerUpdateRequestDto) {
        CustomBurger customBurger = customBurgerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id가 없습니다. id=" + id));
        customBurger.update(customBurgerUpdateRequestDto.getName(), customBurgerUpdateRequestDto.getExplanation(),
                customBurgerUpdateRequestDto.getPrice(), customBurgerUpdateRequestDto.getType(), customBurgerUpdateRequestDto.getPatty(),
                customBurgerUpdateRequestDto.getBread(), customBurgerUpdateRequestDto.getAdditional(), customBurgerUpdateRequestDto.getSource());
        return id;
    }


}
