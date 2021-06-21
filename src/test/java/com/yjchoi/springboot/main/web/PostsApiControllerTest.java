package com.yjchoi.springboot.main.web;

import com.yjchoi.springboot.main.domain.posts.Posts;
import com.yjchoi.springboot.main.domain.posts.PostsRepository;
import com.yjchoi.springboot.main.web.dto.PostsSaveRequestDto;

import com.yjchoi.springboot.main.web.dto.PostsUpdateRequestDto;
import org.junit.After;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After  // 테스트 후 리소스 반납
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
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

        // Request
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // Validate for response header
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // Validate for reponse body
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
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

        // PostsUpdateRequestDto가 요청 바디 타입인 요청 엔티티
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // Request
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // Validate for response header
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // Validate for reponse body
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(changedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(changedContent);
    }

}
