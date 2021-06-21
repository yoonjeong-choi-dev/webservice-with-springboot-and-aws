package com.yjchoi.springboot.main.web;

import com.yjchoi.springboot.main.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController // 컨트롤러를 JSON을 반환하는 컨트롤러 만들어 준다
public class HelloController {

    // GetMapping : GET 메서드(/hello)에 대한 API 생성
    @GetMapping("/hello")   
    public String hello(){
        return "hello";
    }

    // RequestParam 어노테이션에서 설정한 파라미터가 API 호출시 넘어옴
    @GetMapping("/hello/dto") 
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
