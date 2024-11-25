package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
