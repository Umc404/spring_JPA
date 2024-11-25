package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.AuthUserDTO;
import com.ezen.boot_JPA.dto.UserDTO;
import com.ezen.boot_JPA.entity.AuthUser;
import com.ezen.boot_JPA.entity.User;

import java.util.List;

public interface UserService {
    String join(UserDTO userDTO);

    default User convertDtoToEntity(UserDTO udto) {
        return User.builder()
                .email(udto.getEmail())
                .pwd(udto.getPwd())
                .nickName(udto.getNickName())
                .lastLogin(udto.getLastLogin())
                .build();
    }

    /* UserDTO(화면) => authUser(저장)*/
    default AuthUser convertDtoToAuthEntity(UserDTO userDTO) {
        return AuthUser.builder()
                .email(userDTO.getEmail())
                .auth("ROLE_USER")
                .build();
    }

    default AuthUserDTO convertEntityToAuthDto(AuthUser authUser) {
        return AuthUserDTO.builder()
                .email(authUser.getEmail())
                .auth(authUser.getAuth())
                .build();
    }

    /*user(entity) => userDTO*/
    default UserDTO convertEntityToDto(User user, List<AuthUserDTO> authUserDTOList) {
        return UserDTO.builder()
                .email(user.getEmail())
                .pwd(user.getPwd())
                .nickName(user.getNickName())
                .lastLogin(user.getLastLogin())
                .regAt(user.getRegAt())
                .modAt(user.getModAt())
                .authList(authUserDTOList)
                .build();
    }

    UserDTO selectEmail(String username);

    boolean updateLastLogin(String authEmail);

    String userUpdatePwdEmpty(UserDTO userDTO);

    String userUpdate(UserDTO userDTO);
}
