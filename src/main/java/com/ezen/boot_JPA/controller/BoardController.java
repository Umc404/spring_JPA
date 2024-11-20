package com.ezen.boot_JPA.controller;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/register")
    public void register() {    }

    @PostMapping("/register")
    public String register(BoardDTO bdto) {
        log.info("boardDTO > {}", bdto);
        // insert, update, delete => return 1 row
        // jpa insert, update, delete => return id
        Long bno = boardService.insert(bdto);
        log.info(">>>> isOk > {}", bno > 0? "Ok":"Fail");
        return "/index";
    }

    @GetMapping("/list")
    public void list(Model m) {
        List<BoardDTO> boardList = boardService.getList();
        m.addAttribute("list", boardList);
    }

    @GetMapping("/detail")
    public void detail(@RequestParam("bno")long bno, Model m) {
        m.addAttribute("bdto", boardService.getDetail(bno));
    }

    @PostMapping("/modify")
    public String modify(BoardDTO bdto, RedirectAttributes redirectAttributes) {
        Long bno = boardService.modify(bdto);
        redirectAttributes.addAttribute("bno", bdto.getBno());
        log.info(">>> modify setting > {}", bno);
        return "redirect:/board/detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bno")long bno) {
        Long delBno = boardService.delete(bno);
        log.info(">>> delete board > {}", delBno>0? "Ok":"Fail");
        return "redirect:/board/list";
    }
}
