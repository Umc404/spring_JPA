package com.ezen.boot_JPA.controller;

import com.ezen.boot_JPA.dto.UserDTO;
import com.ezen.boot_JPA.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequestMapping("/user/*")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService usv;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public void join() {}


    @PostMapping("/join")
    public String join(UserDTO userDTO) {
        log.info(">> userDTO > {}", userDTO);
        // passwordEncoding
        userDTO.setPwd(passwordEncoder.encode(userDTO.getPwd()));
        String email = usv.join(userDTO);
        log.info(">> email > {}", email);
        return (email == null ? "/user/join":"/index");
    }

    @GetMapping("/login")
    public void login(@RequestParam(value = "error", required = false)String error,
                      @RequestParam(value = "exception", required = false)String exception,
                      Model m){
        /* 에러와 예외값을 담아 화면으로 전달 */
        m.addAttribute("error", error);
        m.addAttribute("exception", exception);
    }

    @GetMapping("/modify")
    public void modify() {}

    @PostMapping("/modify")
    public String modify(UserDTO userDTO) {
        String isOk;
        if(userDTO.getPwd().isEmpty()) {
            isOk = usv.userUpdatePwdEmpty(userDTO);
        } else {
            userDTO.setPwd(passwordEncoder.encode(userDTO.getPwd()));
            isOk = usv.userUpdate(userDTO);
        }
        log.info(">>> update userInfo > {}", isOk);
        log.info(">>> update sign > {}", isOk!=null? "Ok":"Fail");
        return "/index";
    }
}
