package com.ezen.boot_JPA.service;


import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.entity.Board;
import com.ezen.boot_JPA.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public Long insert(BoardDTO bdto) {
        // 저장 객체는 Board
        // save() : insert 후 저장 객체의 id를 리턴. 괄호 안에 엔티티를 넣어야함.
        // save() Entity 객체를 파라미터로 전송
        return boardRepository.save(convertDtoToEntity(bdto)).getBno();
    }

    @Override
    public List<BoardDTO> getList() {
        // 컨트롤러로 보내야 하는 리턴은 List<BoardDTO>
        // DB에서 가져오는 리턴은 List<Board>  =>  BoardDTO 객체로 변환
        // findAll() 사용
        // 가져올 때 정렬 : Sort.by(Sort.Direction.DESC,"정렬 기준 컬렴명")
        List<Board> boardList = boardRepository.findAll(
                Sort.by(Sort.Direction.DESC, "bno"));

        /*List<BoardDTO> boardDTOList = new ArrayList<>();
        for(Board board : boardList) {
            boardDTOList.add(convertEntityToDto(board));
        }*/

        List<BoardDTO> boardDTOList = boardList.stream()
                .map(b -> convertEntityToDto(b)).toList();

        return boardDTOList;
    }

    @Override
    public Object getDetail(long bno) {
        /* findById : 아이디(PK)를 주고 해당 객체를 리턴
           findById의 리턴타입 Optional<Board> 타입으로 리턴
           Optional<T> : nullPointException이 발생하지 않도록 도와줌.
           Optional.isEmpty() : null일 경우 확인 가능 true / false
           Optional.isPresent() : 값이 있는지 확인 true / false
           Optional.get() : board 값(객체) 가져오기
        */
        Optional<Board> optional = boardRepository.findById(bno);
        if(optional.isPresent()) {
            BoardDTO bdto = convertEntityToDto(optional.get());
            return bdto;
        }
        return null;
    }

    @Override
    public Long modify(BoardDTO bdto) {
        // update : jpa는 업데이트가 없음.
        // 기존 객체를 가져와서 set 으로 수정 후 다시 저장.
        Optional<Board> optional = boardRepository.findById(bdto.getBno());
        if(optional.isPresent()) {
            /*Board modBdto = convertEntityToDto(optional.get());
            modBdto.setTitle(bdto.getTitle());
            modBdto.setContent(bdto.getContent());
            return modBdto.getBno(); */

            Board modBoard = optional.get();
            modBoard.setTitle(bdto.getTitle());
            modBoard.setContent(bdto.getContent());

            return boardRepository.save(modBoard).getBno();
        }

        return null;
    }

    // 삭제 : deleteById(id)
    @Override
    public Long delete(long bno) {
        Optional<Board> optional = boardRepository.findById(bno);
        if(optional.isPresent()){
            Long delBno = bno;
            boardRepository.deleteById(bno);
            return bno;
        }
        return null;
    }
}