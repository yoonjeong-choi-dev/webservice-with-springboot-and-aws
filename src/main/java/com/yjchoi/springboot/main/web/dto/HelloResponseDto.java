package com.yjchoi.springboot.main.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 어노테이션을 통해 롬복을 통해 자동 생성할 코드 영역 설정 : 생성자 및 게터
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
