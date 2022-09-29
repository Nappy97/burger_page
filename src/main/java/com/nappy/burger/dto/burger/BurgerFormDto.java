package com.nappy.burger.dto.burger;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.domain.burger.BurgerSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BurgerFormDto {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요")
    private String burgerName;

    @NotBlank(message = "버거의 주재료 타입을 입력해주세요")
    private String burgerType;

    @NotBlank(message = "재료를 골라주세요")
    private String burgerContent;

    @NotBlank(message = "설명을 넣어주세여")
    private String burgerExplanation;

    @NotNull(message = "남은 재고를 입력해주세요")
    private Integer stock;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;

    private BurgerSellStatus burgerSellStatus;

    // 버거 수정 시 사용되는 변수들
    private List<BurgerImgDto> burgerImgDtoList = new ArrayList<>();
    private List<Long> burgerImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // DTO -> Entity
    public Burger createBurger(){
        return modelMapper.map(this, Burger.class);
    }

    // Entity -> DTO
    public static BurgerFormDto of(Burger burger){
        return modelMapper.map(burger, BurgerFormDto.class);
    }

}
