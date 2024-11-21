package com.ezen.boot_JPA.repository;


import com.ezen.boot_JPA.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByBno(Long bno);
//}


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByBno(Long bno, Pageable pageable);
}
