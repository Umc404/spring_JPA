package com.ezen.boot_JPA.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardFileDTO {
    private BoardDTO boardDTO;
    private List<FileDTO> fileDTOList;

}
