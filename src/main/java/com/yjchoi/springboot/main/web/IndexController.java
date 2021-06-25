package com.yjchoi.springboot.main.web;

import lombok.RequiredArgsConstructor;

import com.yjchoi.springboot.main.service.posts.PostsService;
import com.yjchoi.springboot.main.web.dto.PostsResponseDto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    
    @GetMapping("/")
    public String index(Model model) {
        // 서버 템플릿 엔진에 사용할 객체 : {{#posts}} 에 사용할 어트리븃 설정
        model.addAttribute("posts", postsService.findAllDesc());
        
        // 머스테치로 인해, resource/templates/index.mustache가 응답 데이터가 된다
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
    
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        
        // posts-update.mustach 에서 사용할 {{post}}의 어트리븃 설정
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
