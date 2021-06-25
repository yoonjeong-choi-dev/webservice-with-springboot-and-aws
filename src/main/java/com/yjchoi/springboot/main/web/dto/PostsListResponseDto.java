package com.yjchoi.springboot.main.web.dto;

import com.yjchoi.springboot.main.domain.posts.Posts;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor();
        modifiedDate = entity.getModifiedDate();
    }
}
