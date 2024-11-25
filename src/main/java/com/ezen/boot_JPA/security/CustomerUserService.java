package com.ezen.boot_JPA.security;

import com.ezen.boot_JPA.dto.UserDTO;
import com.ezen.boot_JPA.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomerUserService implements UserDetailsService {

    @Autowired
    public UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // username 이 DB에 있는지 확인
        // user 테이블에 email 객체를 가져오기
        UserDTO userDTO = userService.selectEmail(username);
        log.info(">>>> UserDetails login User > {}", userDTO);
        if(userDTO == null) {
            throw new UsernameNotFoundException(username);
        }

        return new AuthMember(userDTO);
    }
}
