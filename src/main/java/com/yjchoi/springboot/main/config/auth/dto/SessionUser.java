package com.yjchoi.springboot.main.config.auth.dto;

import lombok.Getter;

import com.yjchoi.springboot.main.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    // 인증된 사용자 정보보
   private String name;
    private String email;
    private String picuture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picuture = user.getPicture();
    }
}
