package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    List<AuthUser> findByEmail(String email);
}
