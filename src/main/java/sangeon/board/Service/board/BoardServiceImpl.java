package sangeon.board.Service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sangeon.board.entity.board.Board;
import sangeon.board.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public List<Board> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        return boards;
    }
}
