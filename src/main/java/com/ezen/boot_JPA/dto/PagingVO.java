package com.ezen.boot_JPA.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
public class PagingVO {
    private int totalPage;
    private int startPage;
    private int endPage;
    private boolean hasPrev, hasNext;
    private int pageNo;

    private String type;
    private String keyword;

    public PagingVO(Page<BoardDTO> list, int pageNo, String type, String keyword) {
        this.pageNo = pageNo+1;
        this.totalPage = list.getTotalPages();
        this.type = type;
        this.keyword = keyword;

        this.endPage = (int)Math.ceil(this.pageNo/10.0) * 10;
        this.startPage = endPage - 9;
        if(endPage > totalPage) {
            endPage = totalPage;
        }

        this.hasPrev = this.pageNo > 10;
        this.hasNext = this.endPage < this.totalPage;
    }
}
