package sangeon.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sangeon.board.controller.dto.SearchDto;
import sangeon.board.entity.board.Board;
import sangeon.board.entity.member.QMember;

import static sangeon.board.entity.board.QBoard.board;
import static sangeon.board.entity.member.QMember.*;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAll(SearchDto params) {

        return jpaQueryFactory.select(board)
                .from(board)
                .leftJoin(board.member, member).fetchJoin()
                .where(search(params))
                .orderBy(board.createDate.desc())
                .offset(params.getOffset())
                .limit(params.getRecordSize())
                .orderBy(board.createDate.desc())
                .fetch();
    }

    @Override
    public Long recordCount(SearchDto params) {
        return jpaQueryFactory.select(board)
                .from(board)
                .leftJoin(board.member, member).fetchJoin()
                .where(search(params))
                .orderBy(board.createDate.desc())
                .fetchCount();
    }

    private BooleanExpression search(SearchDto params) {
        if(params.getSearchType() != null) {
            switch (params.getSearchType()) {
                case "title":
                    return board.title.like("%" + params.getKeyword() + "%");
                case "content":
                    return board.content.like("%" + params.getKeyword() + "%");
                case "email":
                    return board.member.email.like("%" + params.getKeyword() + "%");
            }

        }
        return null;
    }

}
