package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.CommentDTO;
import com.ezen.boot_JPA.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {



    default Comment convertCdtoToEntity(CommentDTO cdto) {
        return Comment.builder()
                .cno(cdto.getCno())
                .bno(cdto.getBno())
                .writer(cdto.getWriter())
                .content(cdto.getContent())
                .build();
    }


    default CommentDTO convertEntityToCdto(Comment comment) {
        return CommentDTO.builder()
                .cno(comment.getCno())
                .bno(comment.getBno())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .regAt(comment.getRegAt())
                .modAt(comment.getModAt())
                .build();
    }

    long post(CommentDTO cdto);

    Page<CommentDTO> getList(long bno, int page);

    Long update(CommentDTO cdto);

    long delete(long cno);

//    List<CommentDTO> getList(Long bno);
}
