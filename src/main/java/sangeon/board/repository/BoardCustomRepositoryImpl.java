package sangeon.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sangeon.board.controller.dto.SearchDto;
import sangeon.board.entity.board.Board;
import static sangeon.board.entity.board.QBoard.board;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAll(SearchDto params) {
        return jpaQueryFactory.selectFrom(board)
                .offset(params.getOffset())
                .limit(params.getRecordSize())
                .orderBy(board.createDate.desc())
                .fetch();
    }

    @Override
    public Long recordCount(SearchDto params) {
        return jpaQueryFactory
                .select(board.count())
                .from(board)
                .fetchFirst();
    }

}
