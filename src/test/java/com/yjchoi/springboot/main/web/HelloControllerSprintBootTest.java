package com.yjchoi.springboot.main.web;

import com.yjchoi.springboot.main.web.dto.HelloResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
* CAUTION!!!!!!
- 주석 처리한 부분은 소셜 로그인 서비스(CustomOAuth2UserService) 적용 이전에 가능했던 테스트
- mvc 사용하지 않고 테스트 하던 부분
=> mvc를 테스트이전에 만들어 주는 방식으로 MockMvc 사용
 */

@RunWith(SpringRunner.class)    // 스프링부트와 JUnit 사이의 연결자 역할 : SrpingRunner 실행자를 사용하여 JUnit 테스트
//@WebMvcTest(controllers = HelloController.class)    // Web에 집중할 수 있는 어노테이션 : 테스트할 컨트롤러 등록
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerSprintBootTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;    // 웹 API 테스트시 사용
    
    @Before // 테스트 이전 실행 : 테스트 시작 전에 MockMvc 인스턴스 생성
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")   // 가짜 사용자 등록
    public void return_hello() throws Exception {
        String answer = "hello";
        String url = "http://localhost:"+port+"/hello";

//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isEqualTo(answer);

        // 체이닝을 통한 api 테스트
        mvc.perform(get(url))          // api 호출
                .andExpect(status().isOk())           // http 헤더의 status 검증
                .andExpect(content().string(answer)); // http 응답 바디 검증

    }


    @Test
    @WithMockUser(roles = "USER")   // 가짜 사용자 등록
    public void return_helloDto() throws Exception {
        String name = "YJ";
        int amount = 29;

        String url = "http://localhost:"+port+"/hello/dto";
//        String uri = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("name", name)
//                .queryParam("amount", amount)
//                .build().toString();

//        ResponseEntity<HelloResponseDto> responseEntity = restTemplate.getForEntity(uri, HelloResponseDto.class);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody().getName()).isEqualTo(name);
//        assertThat(responseEntity.getBody().getAmount()).isEqualTo(amount);

        mvc.perform(
                get(url)
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))   // get의 체니잉 메서드 param을 이용하여 요청 파라미터 설정
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
