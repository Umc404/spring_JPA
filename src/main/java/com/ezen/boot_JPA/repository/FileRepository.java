package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.Comment;
import com.ezen.boot_JPA.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, String> {
    List<File> findByBno(Long bno);
}
