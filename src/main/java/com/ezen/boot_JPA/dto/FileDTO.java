package com.ezen.boot_JPA.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private String uuid;
    private String saveDir;
    private String fileName;
    private int fileType;
    private long bno;
    private long fileSize;
    private LocalDateTime regAt;
    private LocalDateTime modAt;

}
