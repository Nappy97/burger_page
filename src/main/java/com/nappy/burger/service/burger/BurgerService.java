package com.nappy.burger.service.burger;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.domain.burger.BurgerImg;
import com.nappy.burger.dto.burger.BurgerFormDto;
import com.nappy.burger.dto.burger.BurgerImgDto;
import com.nappy.burger.dto.burger.BurgerSearchDto;
import com.nappy.burger.dto.burger.MainBurgerDto;
import com.nappy.burger.repository.burger.BurgerImgRepository;
import com.nappy.burger.repository.burger.BurgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BurgerService {

    private final BurgerRepository burgerRepository;
    private final BurgerImgService burgerImgService;
    private final BurgerImgRepository burgerImgRepository;

    // 버거 등록
    public Long saveBurger(BurgerFormDto burgerFormDto, List<MultipartFile> burgerImgFileList) throws Exception {

        // 상품등록 (순서중요)
        Burger burger = burgerFormDto.createBurger();
        burgerRepository.save(burger);

        // 이미지 등록
        for (int i = 0; i < burgerImgFileList.size(); i++) {
            BurgerImg burgerImg = new BurgerImg();
            burgerImg.setBurger(burger);
            if (i == 0) {
                burgerImg.setRepimgYn("Y");
            } else {
                burgerImg.setRepimgYn("N");
            }
            burgerImgService.saveBurgerImg(burgerImg, burgerImgFileList.get(i));
        }
        return burger.getId();
    }

    // 버거 메뉴 관리 페이지 목록 조회
    @Transactional(readOnly = true)
    public Page<Burger> getAdminBrugerPage(BurgerSearchDto burgerSearchDto, Pageable pageable){
        return burgerRepository.getAdminBurgerPage(burgerSearchDto, pageable);
    }

    // 조회
    @Transactional(readOnly = true)
    public BurgerFormDto getBurgerDetail(Long burgerId){

        // 이미지 Entity 들을 burgerImgDto 로 변환하여 burgerImgDtoList 에 담기
        List<BurgerImg> burgerImgList = burgerImgRepository.findByBurgerIdOrderByIdAAsc(burgerId);
        List<BurgerImgDto> burgerImgDtoList = new ArrayList<>();

        for (BurgerImg burgerImg : burgerImgList){
            BurgerImgDto burgerImgDto = BurgerImgDto.of(burgerImg);
            burgerImgDtoList.add(burgerImgDto);
        }

        // burger Entity 를 BurgerFormDto 객체로 변환 후 burgerImgDtoList 멤버변수를 초기화
        Burger burger = burgerRepository.findById(burgerId).orElseThrow(EntityNotFoundException::new);
        BurgerFormDto burgerFormDto = BurgerFormDto.of(burger);
        burgerFormDto.setBurgerImgDtoList(burgerImgDtoList);
        return burgerFormDto;
    }


    // 수정 하기
    public Long updateBurger(BurgerFormDto burgerFormDto, List<MultipartFile> burgerImgFileList) throws IOException{

        // 수정
        Burger burger = burgerRepository.findById(burgerFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        burger.updateBurger(burgerFormDto);

        // 이미지 수정
        List<Long> burgerImgIds = burgerFormDto.getBurgerImgIds();
        for (int i = 0; i< burgerImgFileList.size(); i++){
            burgerImgService.updateBurgerImg(burgerImgIds.get(i), burgerImgFileList.get(i));
        }

        return burger.getId();
    }

    // 메인 페이지 상품 목록 조회
    @Transactional(readOnly = true)
    public Page<MainBurgerDto> getMainBurgerPage(BurgerSearchDto burgerSearchDto, Pageable pageable){
        return burgerRepository.getMainBurgerPage(burgerSearchDto, pageable);
    }
}
