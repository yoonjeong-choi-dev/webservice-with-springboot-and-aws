package com.yjchoi.springboot.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// MockMvc 테스트를 사용하기 위해 분리한 Jpa용 클래스
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
