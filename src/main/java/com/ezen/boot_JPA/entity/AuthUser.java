package com.ezen.boot_JPA.entity;

import jakarta.persistence.*;
import lombok.*;

// @Entity만 선언했을 시, 클래스명 대로 테이블을 생성함 : AuthUser
@Entity
@Table(name = "auth_user")          // @Table 선언 후 이름 정할 시 이름대로 테이블이 생성. : auth_user
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 200, nullable = false)
    private String email;
    @Column(length = 50, nullable = false)
    private String auth;
}
