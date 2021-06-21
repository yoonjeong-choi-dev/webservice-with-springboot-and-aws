package com.yjchoi.springboot.main.web.dto;

import com.yjchoi.springboot.main.domain.posts.Posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String author;
    private String title;
    private String content;

    // 빌더 패턴을 이용한 엔티티 클래스 생성자 정의
    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Dto -> Entity 변환
    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
