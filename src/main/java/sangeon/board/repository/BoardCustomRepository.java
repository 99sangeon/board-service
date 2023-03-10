package sangeon.board.repository;

import sangeon.board.controller.dto.SearchDto;
import sangeon.board.entity.board.Board;

import java.util.List;

public interface BoardCustomRepository {

    List<Board> findAll(SearchDto params);
    Long recordCount(SearchDto params);
}
