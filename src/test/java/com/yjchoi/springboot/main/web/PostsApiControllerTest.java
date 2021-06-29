package com.yjchoi.springboot.main.web;

import com.yjchoi.springboot.main.domain.posts.Posts;
import com.yjchoi.springboot.main.domain.posts.PostsRepository;
import com.yjchoi.springboot.main.web.dto.PostsSaveRequestDto;
import com.yjchoi.springboot.main.web.dto.PostsUpdateRequestDto;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before // 테스트 이전 실행 : 테스트 시작 전에 MockMvc 인스턴스 생성
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After  // 테스트 후 리소스 반납
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")   // 가짜 사용자 등록
    public void Posts_register() throws Exception {
        String title = "Test Title";
        String content = "Test Content";

        // 요청 생성
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("YJ Choi")
                .build();

        // Post url
        String url = "http://localhost:" + port + "/api/v1/posts";

        // Request : 생성된 MockMvc를 통해 API 테스트
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // Validate for response body
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_update() throws Exception {
        // Update 테스트를 위한 데이터 생성
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("Original Title")
                .content("Orignal Content")
                .author("YJ Choi")
                .build());

        // Update 요청을 위한 dto 생성
        Long updateId = savedPost.getId();
        String changedTitle = "Changed Title";
        String changedContent = "Changed Content";
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(changedTitle)
                .content(changedContent)
                .build();

        // Update url
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        // Request : 생성된 MockMvc를 통해 API 테스트
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // Validate for response body
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(changedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(changedContent);
    }

}
