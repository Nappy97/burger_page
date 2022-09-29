package com.nappy.burger.service.burger;

import com.nappy.burger.domain.burger.BurgerImg;
import com.nappy.burger.repository.burger.BurgerImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class BurgerImgService {

    @Value("${burgerImgLocation}")
    private String burgerImgLocation;

    private final FileService fileService;

    private final BurgerImgRepository burgerImgRepository;

    // 이미지 저장
    public void saveBurgerImg(BurgerImg burgerImg, MultipartFile burgerImgFile) throws IOException {
        String oriImgName = burgerImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(burgerImgLocation, oriImgName, burgerImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        // 상품 이미지 정보저장
        burgerImg.updateBurgerImg(oriImgName, imgName, imgUrl);
        burgerImgRepository.save(burgerImg);
    }

    // 이미지 수정
    public void updateBurgerImg(Long burgerImgId, MultipartFile burgerImgFile) throws IOException {

        // 수정 시도
        if (!burgerImgFile.isEmpty()){
            BurgerImg savedBurgerImg = burgerImgRepository.findById(burgerImgId).orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 존재식 삭제
            if (!StringUtils.isEmpty(savedBurgerImg.getImgName())){
                fileService.deleteFile(burgerImgLocation + "/" + savedBurgerImg);
            }

            String oriImgName = burgerImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(burgerImgLocation, oriImgName, burgerImgFile.getBytes());
            String imgUrl = "/images/item" + imgName;
            savedBurgerImg.updateBurgerImg(oriImgName, imgName, imgUrl);
        }
    }

}
