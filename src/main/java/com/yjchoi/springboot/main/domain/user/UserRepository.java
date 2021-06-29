package com.yjchoi.springboot.main.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// User 클래스로 DB를 접근하게 해줄 JpaRepository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // email을 통해 해당 사용자의 가입 여부를 판단하는 메서드
    Optional<User> findByEmail(String email);
}
