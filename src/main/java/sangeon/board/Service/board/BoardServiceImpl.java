package sangeon.board.Service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sangeon.board.Service.dto.BoardFormDto;
import sangeon.board.controller.dto.BoardListDto;
import sangeon.board.entity.board.Board;
import sangeon.board.entity.member.Member;
import sangeon.board.repository.BoardRepository;
import sangeon.board.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<BoardListDto> getBoardList() {
        List<BoardListDto> boards = new ArrayList<>();

        for(Board board : boardRepository.findAll()){
            boards.add(BoardListDto.of(board));
        }
        return boards;
    }


    @Override
    public void writeBoard(BoardFormDto boardFormDto) {
        Member member = memberRepository.findOneByEmail(boardFormDto.getEmail()).get();
        Board board = boardFormDto.toEntity();
        board.setMember(member);
        boardRepository.save(board);
    }
}
