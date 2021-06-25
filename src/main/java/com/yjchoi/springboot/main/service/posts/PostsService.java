package com.yjchoi.springboot.main.service.posts;

import com.yjchoi.springboot.main.domain.posts.Posts;
import com.yjchoi.springboot.main.domain.posts.PostsRepository;
import com.yjchoi.springboot.main.web.dto.PostsResponseDto;
import com.yjchoi.springboot.main.web.dto.PostsSaveRequestDto;
import com.yjchoi.springboot.main.web.dto.PostsUpdateRequestDto;
import com.yjchoi.springboot.main.web.dto.PostsListResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    // RequiredArgsConstructor에 의해 PostsRepository 의존성이 주입
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException(("해당 개시글은 없습니다. id=" + id)));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException(("해당 개시글은 없습니다. id=" + id)));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return  id;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException(("해당 개시글은 없습니다. id=" + id)));
        postsRepository.delete(posts);
    }
}
