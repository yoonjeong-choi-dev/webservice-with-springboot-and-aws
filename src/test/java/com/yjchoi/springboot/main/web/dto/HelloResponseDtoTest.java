package com.yjchoi.springboot.main.web.dto;

import com.yjchoi.springboot.main.dto.HelloResponseDto;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void lombok_execution_test(){
        String name = "test";
        int amount = 1000;

        // 테스트용 dto 생성
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // 테스트
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
