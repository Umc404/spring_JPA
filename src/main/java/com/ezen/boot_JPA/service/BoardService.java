package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.entity.Board;

import java.util.List;

public interface BoardService {
    // 추상메서드만 가능한 인터페이스
    // 메서드가 default(접근제한자) 구현 가능
    Long insert(BoardDTO bdto);

    // BoardDTO(class) : bno title writer content regAt modAt
    // Board(table) : bno title writer content
    // BoardDTO => board 변환
    // 화면에서 가져온 BoardDTO 객체를 저장을 위한 Board 객체로 변환
    default Board convertDtoToEntity(BoardDTO bdto) {
        return Board.builder()
                .bno(bdto.getBno())
                .title(bdto.getTitle())
                .writer(bdto.getWriter())
                .content(bdto.getContent())
                .build();
    }

    // board => BoardDTO 전환
    // DB에서 가져온 Board 객체를 화면에 뿌리기 위한 BoardDTO 객체로 변환
    default BoardDTO convertEntityToDto(Board board){
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .regAt(board.getRegAt())
                .modAt(board.getModAt())
                .build();
    }

    List<BoardDTO> getList();

    Object getDetail(long bno);

    Long modify(BoardDTO bdto);

    Long delete(long bno);
}
