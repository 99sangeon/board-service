package sangeon.board.Service.board;

import sangeon.board.Service.dto.BoardFormDto;
import sangeon.board.controller.dto.BoardViewDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface BoardService {

   List<BoardViewDto> getBoardList();

   BoardViewDto getDetails(Long id);

    void writeBoard(BoardFormDto boardFormDto);

    Boolean deleteBoard(HttpServletRequest request, Long id);
}
