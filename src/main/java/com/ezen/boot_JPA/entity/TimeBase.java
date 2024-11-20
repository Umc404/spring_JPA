package com.ezen.boot_JPA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value= {AuditingEntityListener.class})     // 반드시 지정
@Getter
public class TimeBase {
    /* 등록일, 수정일만 따로 빼서 관리하는 슈퍼 테이블 */

    @CreatedDate
    @Column(name = "reg_at", updatable = false)
    private LocalDateTime regAt;

    @LastModifiedDate
    @Column(name = "mod_at")
    private LocalDateTime modAt;
}
