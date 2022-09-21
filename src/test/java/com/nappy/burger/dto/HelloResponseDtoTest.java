package com.nappy.burger.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class HelloResponseDtoTest {

    @Test
    public void 롬복_테스트(){
        // given
        String name = "uihyung";
        String nickname = "nappy";

        // when
        HelloResponseDto helloResponseDto = new HelloResponseDto(name, nickname); // 필드가 포함된 생성자 생성

        // then
        assertThat(helloResponseDto.getName()).isEqualTo(name);
        assertThat(helloResponseDto.getNickname()).isEqualTo(nickname);
    }
}
