// LoginUserArgumentResolver를 스프링에서 인식하게 하기 위한 설정 클래스
package com.yjchoi.springboot.main.config;

import lombok.RequiredArgsConstructor;

import com.yjchoi.springboot.main.config.auth.LoginUserArgumentResolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        // HandlerMethodArgumentResolver 구현체는 WebMvcConfigurer의 addArgumentResolvers를 통해 추가
        argumentResolvers.add(loginUserArgumentResolver);
    }

}
