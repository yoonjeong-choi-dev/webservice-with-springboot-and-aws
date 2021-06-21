package com.yjchoi.springboot.main.domain.posts;

import com.yjchoi.springboot.main.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


// Post : 게터 생성, 기본 생성자(중괄호 초기화) 및 JPA를 위한 어노테이션
@Getter
@NoArgsConstructor
@Entity // DB 테이블과 매칭되는 클래스 : DB 테이블은 클래스의 카멜케이스 이름이 언더스코어 네이밍으로 변경된 이름의 테이블로 연결
public class Posts extends BaseTimeEntity {
    // PK 필드(@ID) with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 디폴트 필드 : 어노테이션을 선언하지 않아도 자동으로 테이블 필드(VARCHAR(255))로 간주
    private String author;

    // @Column 을 통해 테이블 필드 타입 설정
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // Update 메서드
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    // 빌더 패턴을 이용한 엔티티 클래스 생성자 정의의
    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
