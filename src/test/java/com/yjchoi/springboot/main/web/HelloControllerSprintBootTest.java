package com.yjchoi.springboot.main.web;

import com.yjchoi.springboot.main.web.dto.HelloResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)    // 스프링부트와 JUnit 사이의 연결자 역할 : SrpingRunner 실행자를 사용하여 JUnit 테스트
//@WebMvcTest(controllers = HelloController.class)    // Web에 집중할 수 있는 어노테이션 : 테스트할 컨트롤러 등록
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerSprintBootTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mvc;    // 웹 API 테스트시 사용

    @Test
    public void return_hello() throws Exception {
        String answer = "hello";
        String url = "http://localhost:"+port+"/hello";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(answer);

    }

    // TODO
    @Test
    public void return_helloDto() throws Exception {
        String name = "YJ";
        int amount = 29;

        String url = "http://localhost:"+port+"/hello/dto";
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", name)
                .queryParam("amount", amount)
                .build().toString();

        ResponseEntity<HelloResponseDto> responseEntity = restTemplate.getForEntity(uri, HelloResponseDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo(name);
        assertThat(responseEntity.getBody().getAmount()).isEqualTo(amount);


    }
}
