package com.yjchoi.springboot.main.web;

import com.yjchoi.springboot.main.service.posts.PostsService;
import com.yjchoi.springboot.main.web.dto.PostsResponseDto;
import com.yjchoi.springboot.main.web.dto.PostsSaveRequestDto;
import com.yjchoi.springboot.main.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    // RequiredArgsConstructor에 의해 PostsService 의존성이 주입
    private final PostsService postsService;

    // POST API 정의
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    // GET API 정의
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    // PUT API 정의
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    // DELETE API 정의
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }
}
