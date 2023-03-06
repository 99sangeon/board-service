package sangeon.board.Service.board;

import sangeon.board.Service.dto.BoardFormDto;
import sangeon.board.controller.dto.BoardListDto;

import java.util.List;

public interface BoardService {

   List<BoardListDto> getBoardList();

    void writeBoard(BoardFormDto boardFormDto);
}
