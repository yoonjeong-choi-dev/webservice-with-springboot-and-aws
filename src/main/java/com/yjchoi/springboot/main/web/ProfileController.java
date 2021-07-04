package com.yjchoi.springboot.main.web;

import lombok.RequiredArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        // 현재 실행 중인 ActiveProfile을 가져옴
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        // 배포에 사용될 프로파일 설정
        // real : 무중단 배포 이전 설정파일
        // real1, real2 : green-blue 배포를 위한 설정파일
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
