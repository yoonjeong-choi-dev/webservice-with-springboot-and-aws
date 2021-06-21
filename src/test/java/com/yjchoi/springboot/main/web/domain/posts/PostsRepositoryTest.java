package com.yjchoi.springboot.main.web.domain.posts;

import com.yjchoi.springboot.main.domain.posts.Posts;
import com.yjchoi.springboot.main.domain.posts.PostsRepository;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest     // 기본 설정시, H2 DB로 실행
public class PostsRepositoryTest {
    @Autowired  // 빈(IOC 컨테이너가 관리하는 객체) 주입
    PostsRepository postsRepository;

    @After      // 테스트 이후 수행되는 메서드
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void post_save_and_load(){
        String title = "Test Title";
        String content = "Test Content";

        // Save : INSERT Query
        postsRepository.save(Posts.builder().title(title).content(content).author("YJ Choi").build());
        postsRepository.save(Posts.builder().title("Hello").content("World!").author("Another Choi").build());

        // SELECT ALL Query
        List<Posts> postsList = postsRepository.findAll();

        // Validate
        Posts post = postsList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_register() {

        // 테스트용 데이터
        LocalDateTime now = LocalDateTime.of(2019, 6,4, 0,0,0);
        postsRepository.save(Posts.builder()
        .title("Test title")
        .content("Test Content")
        .author("Test Author")
        .build());

        // 저장된 테스트 데이터 select
        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get(0);

        System.out.println(">>>>>>>> createDate="+post.getCreatedDate()+", modifiedDate="+post.getModifiedDate());
        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}
