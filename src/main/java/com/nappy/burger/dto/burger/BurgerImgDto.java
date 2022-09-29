package com.nappy.burger.dto.burger;

import com.nappy.burger.domain.burger.BurgerImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class BurgerImgDto {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    // Entity -> DTO
    public static BurgerImgDto of(BurgerImg burgerImg){
        return modelMapper.map(burgerImg, BurgerImgDto.class);
    }
}
