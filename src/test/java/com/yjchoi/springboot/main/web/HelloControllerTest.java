package com.yjchoi.springboot.main.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@RunWith(SpringRunner.class)    // 스프링부트와 JUnit 사이의 연결자 역할 : SrpingRunner 실행자를 사용하여 JUnit 테스트
@WebMvcTest(controllers = HelloController.class)    // Web에 집중할 수 있는 어노테이션 : 테스트할 컨트롤러 등록
public class HelloControllerTest {
    @Autowired  // 빈(IOC 컨테이너가 관리하는 객체) 주입
    private MockMvc mvc;    // 웹 API 테스트시 사용

    @Test
    public void return_hello() throws Exception {
        String answer = "hello";

        // 체이닝을 통한 api 테스트
        mvc.perform(get("/hello"))          // api 호출
                .andExpect(status().isOk())           // http 헤더의 status 검증
                .andExpect(content().string(answer)); // http 응답 바디 검증
    }

    @Test
    public void return_helloDto() throws Exception {
        String name = "YJ";
        int amount = 29;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))   // get의 체니잉 메서드 param을 이용하여 요청 파라미터 설정
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
