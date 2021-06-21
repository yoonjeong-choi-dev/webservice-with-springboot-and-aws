package com.yjchoi.springboot.main.domain;

import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // 이 클래스를 상속받는 엔티티에는 이 클래스의 필드들이 칼럼으로 자동 인식
@EntityListeners(AuditingEntityListener.class)  // JPA Auditing 기능 추가
// 모든 Entity의 상위 클래스가 되어 Entity 클래스들의 생성/수정 시간을 자동으로 관리하는 역할
public class BaseTimeEntity {
    @CreatedDate    // 엔티티(객체)가 생성될 때, 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate   // 엔티티(객체)의 값(필드)가 수정될 때, 시간 자동 저장
    private LocalDateTime modifiedDate;
}
