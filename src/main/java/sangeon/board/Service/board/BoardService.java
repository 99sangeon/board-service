package sangeon.board.Service.board;

import sangeon.board.controller.dto.BoardDto;
import sangeon.board.controller.dto.BoardUpdateDto;
import sangeon.board.entity.board.Board;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface BoardService {
    List<BoardDto> getBoardList();
    Long saveBoard(HttpSession session, BoardDto boardDto);
    BoardDto getBoard(Long id);
    void update(HttpSession session, Long id, BoardUpdateDto boardUpdateDto);
    void delete(HttpSession session, Long id);
    Boolean isSessionMemberEquBoardMember(HttpSession session, Long board_id);
}
