// URL별 권한 관리 : 로그인/비로그인
package com.yjchoi.springboot.main.config.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.yjchoi.springboot.main.domain.user.Role;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정 활성화 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()    // URL 별 권한 관리를 설정하는 옵션의 시작점 : 권환 관리 대상 설정
                    .antMatchers("/", "/css/**", "/images/**",
                            "/js/**", "/h2-console/**", "/profile").permitAll() // "/" 에 대해서는 전체 열람
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // "api/v1"(앱 api)에 대해서는 USER 권한을 가진 사람만 열람 가능
                    .anyRequest().authenticated()   // 나머지 url에 대한 설정 : 로그인한 사용자만 접근 가능
                .and()
                    .logout().logoutSuccessUrl("/") // 로그아웃 기능에 대한 설정 진입점
                .and()
                    .oauth2Login()  // OAuth2 로그인에 대한 설정 진입점
                    .userInfoEndpoint()// OAuth2 로그인 성공 후 사용자 정보를 가져올 때의 설정
                    .userService(customOAuth2UserService);  // OAuth2 로그인 성공 후 진행할 UserService 인터페이스 구현체 등록
   }
}
