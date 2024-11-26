package com.ezen.boot_JPA.controller;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.BoardFileDTO;
import com.ezen.boot_JPA.dto.FileDTO;
import com.ezen.boot_JPA.dto.PagingVO;
import com.ezen.boot_JPA.handler.FileHandler;
import com.ezen.boot_JPA.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final FileHandler fileHandler;

    @GetMapping("/register")
    public void register() {    }

//    @PostMapping("/register")
//    public String register(BoardDTO bdto) {
//        log.info("boardDTO > {}", bdto);
//        // insert, update, delete => return 1 row
//        // jpa insert, update, delete => return id
//        Long bno = boardService.insert(bdto);
//        log.info(">>>> isOk > {}", bno > 0? "Ok":"Fail");
//        return "/index";
//    }

    @PostMapping("/register")
    public String register(BoardDTO bdto, @RequestParam(value = "files", required = false)
                            MultipartFile[] files) {
        List<FileDTO> flist = null;
        if(files!= null && files[0].getSize() > 0) {
            // 파일 핸들러 작업
            flist = fileHandler.uploadFiles(files);
        }
        long bno = boardService.insert(new BoardFileDTO(bdto, flist));
        return "/index";
    }

    // 페이징 안한 리스트 출력 처리
/*    @GetMapping("/list")
    public void list(Model m) {
        List<BoardDTO> boardList = boardService.getList();
        m.addAttribute("list", boardList);
    }*/

//    @GetMapping("/list")
//    public void detail(@RequestParam(value = "pageNo",
//            defaultValue = "0", required = false)int pageNo, Model m) {
//        // 화면에서 들어오는 pageNo = 1 / 0으로 처리되야함.
//        // 화면에서 들어오는 pageNo = 2 / 1으로 처리되야함.
//        log.info(">> pageNo > {}", pageNo);
//        pageNo = (pageNo == 0 ? 0 : pageNo - 1);
//        log.info(">> pageNo > {}", pageNo);
//
//        Page<BoardDTO> list = boardService.getList(pageNo);
//
//        log.info(">>>> list > {}", list.toString());
//        log.info(">>>> totalCount > {}", list.getTotalElements());      // 전체 글 수
//        log.info(">>>> totalPage > {}", list.getTotalPages());          // 전체 페이지 수 => realEndPage
//        log.info(">>>> pageNumber > {}", list.getNumber());             // 전체 페이지 번호 => pageNo
//        log.info(">>>> pageSize > {}", list.getSize());                 // 한 페이지에 표시되는 길이 => qty
//        log.info(">>>> next > {}", list.hasNext());                     // next 여부
//        log.info(">>>> prev > {}", list.hasPrevious());                 // prev 여부
//
//        PagingVO pgvo = new PagingVO(list, pageNo);
//
//        m.addAttribute("list", list);
//        m.addAttribute("pgvo", pgvo);
//    }

    @GetMapping("/list")
    public void detail(@RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
                       @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       Model m) {

        pageNo = (pageNo == 0 ? 0 : pageNo - 1);
        Page<BoardDTO> list = boardService.getList(pageNo, type, keyword);     // type, keyword 추가하여 ServiceImpl로 송신
        PagingVO pgvo = new PagingVO(list, pageNo, type, keyword);
        m.addAttribute("list", list);
        m.addAttribute("pgvo", pgvo);

    }

    @GetMapping("/detail")
    public void detail(@RequestParam("bno")Long bno, Model m) {
        BoardFileDTO boardFileDTO = boardService.getDetail(bno);
        m.addAttribute("boardFileDTO", boardFileDTO);
    }

    @PostMapping("/modify")
    public String modify(BoardDTO bdto, @RequestParam(name = "files", required = false)
                MultipartFile[] files, RedirectAttributes redirectAttributes) {
        List<FileDTO> flist = null;
        if(files != null && files[0].getSize() > 0) {
            flist = fileHandler.uploadFiles(files);
        }

        Long bno = boardService.modify(new BoardFileDTO(bdto, flist));
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
