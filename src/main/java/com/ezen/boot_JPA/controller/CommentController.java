package com.ezen.boot_JPA.controller;


import com.ezen.boot_JPA.dto.CommentDTO;
import com.ezen.boot_JPA.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment/*")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post")
    @ResponseBody
    public String post(@RequestBody CommentDTO cdto) {
        log.info("{}",cdto);
        long cno = commentService.post(cdto);
        return cno > 0 ? "1":"0";
    }

    @GetMapping("/list/{bno}/{page}")
    @ResponseBody                       // 안들어가면 주소값에 리스트가 뜨지 않음. : []
    public Page<CommentDTO> list(@PathVariable("bno")long bno, @PathVariable("page")int page) {
        // page = 0 부터
        // 1 page => 0 / 2page => 1
        page = (page == 0 ? 0 : page-1);
//        List<CommentDTO> list = commentService.getList(bno);
        Page<CommentDTO> list = commentService.getList(bno, page);
        log.info(">>>> list > {}", list);
        return list;
    }

    @PutMapping("/update")
    @ResponseBody
    public String update(@RequestBody CommentDTO cdto) {
        Long isOk = commentService.update(cdto);
        return isOk > 0 ? "1":"0";
    }

    @DeleteMapping("/delete/{cno}")
    @ResponseBody
    public String delete(@PathVariable("cno")long cno) {
        long isOk = commentService.delete(cno);
        return isOk > 0 ? "0":"1";
    }
}
