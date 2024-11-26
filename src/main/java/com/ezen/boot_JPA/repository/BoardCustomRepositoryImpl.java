package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ezen.boot_JPA.entity.QBoard.board;

public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // BoardCustomRepository 에 선언된 메서드 실제 구현
    @Override
    public Page<Board> searchBoard(String type, String keyword, Pageable pageable) {
        // 조건이 많을 경우
        // select * from board
        // where isDel = 'n' and title like '%abc%'
        // 선행조건 발생시 BooleanExpression 에 먼저 선언. 없으면 null.
        // BooleanExpression condition = board.isDel.eq('n');
        // condition = condition.and
        BooleanExpression condition = null;

        if(type != null && keyword != null) {
//            condition = switch (type) {
//                case "t" -> board.title.containsIgnoreCase(keyword);
//                case "w" -> board.title.containsIgnoreCase(keyword);
//                case "c" -> board.title.containsIgnoreCase(keyword);
//                default -> condition;
//            };
            String[] typearr = type.split("");
            BooleanExpression dynamicCondition = null;
            for(String t : typearr) {
                switch(t) {
                    case "t":
                        dynamicCondition = ( dynamicCondition == null ? board.title.containsIgnoreCase(keyword)
                                : dynamicCondition.or(board.title.containsIgnoreCase(keyword)) );
                        break;
                    case "w":
                        dynamicCondition = ( dynamicCondition == null ? board.writer.containsIgnoreCase(keyword)
                        : dynamicCondition.or(board.writer.containsIgnoreCase(keyword)) );
                        break;
                    case "c":
                        dynamicCondition = ( dynamicCondition == null ? board.content.containsIgnoreCase(keyword)
                        : dynamicCondition.or(board.content.containsIgnoreCase(keyword)) );
                        break;
                }
                if(dynamicCondition != null) {
//                    condition = condition.and(dynamicCondition);
                    condition = dynamicCondition;
                }
            }
        }
        List<Board> result = queryFactory
                .selectFrom(board)
                .where(condition)
                .orderBy(board.bno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 검색된 데이터의 전체 개수 조회
        long total = queryFactory
                .selectFrom(board)
                .where(condition)
                .fetch().size();
        // .fetchCount() => fetch().size()
        return new PageImpl<>(result,pageable,total);
    }
}
