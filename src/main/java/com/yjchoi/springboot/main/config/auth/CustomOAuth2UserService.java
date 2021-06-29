package com.yjchoi.springboot.main.config.auth;

import com.yjchoi.springboot.main.config.auth.dto.OAuthAttributes;
import com.yjchoi.springboot.main.config.auth.dto.SessionUser;
import com.yjchoi.springboot.main.domain.user.User;
import com.yjchoi.springboot.main.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 구글 로그인 후 사용자의 정보를 기반으로 가입, 정보수정, 세션 저장등의 기능 지원
        OAuth2UserService delegate = new DefaultOAuth2UserService();   
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 로그인 진행중인 서비스
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails() 
                .getUserInfoEndpoint().getUserNameAttributeName();  // 로그인 진행 시 키가 되는 필드값
        
        // Oahtu2UserService를 통해 가져온 OAuth2User의 attribute을 담는 객체
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        
        // 해당 유저를 가입 or 업데이트
        User user = saveOrUpdate(attributes);
        
        // SessionUser : 세션에 사용자 정보를 담기위한 Dto 클래스
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
