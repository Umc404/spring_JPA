package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.CommentDTO;
import com.ezen.boot_JPA.entity.Comment;
import com.ezen.boot_JPA.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public long post(CommentDTO cdto) {
        return commentRepository.save(convertCdtoToEntity(cdto)).getCno();
    }

    @Override
    public Page<CommentDTO> getList(long bno, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("cno").descending());
        Page<Comment> list = commentRepository.findByBno(bno, pageable);
        Page<CommentDTO> dtoList = list.map(c -> convertEntityToCdto(c));
        return dtoList;
    }

    @Override
    public Long update(CommentDTO cdto) {

        Optional<Comment> optional = commentRepository.findById(cdto.getCno());
        if(optional.isPresent()) {
            Comment updComment = optional.get();
            updComment.setContent(cdto.getContent());

            return commentRepository.save(updComment).getCno();
        }
        return null;
    }

    @Override
    public long delete(long cno) {
        commentRepository.deleteById(cno);
        Optional<Comment> opt = commentRepository.findById(cno);

        return opt.map(Comment::getCno).orElse(0L);
    }

//    @Override
//    public List<CommentDTO> getList(Long bno) {
//        // select * from comment where bno = #{bno}
//        List<Comment> list = commentRepository.findByBno(bno);
//        List<CommentDTO> dtoList = list.stream().map(c -> convertEntityToCdto(c)).toList();
//        return dtoList;
//    }
}
