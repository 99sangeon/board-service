package sangeon.board.Service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sangeon.board.OAuth.SessionMember;
import sangeon.board.Service.dto.BoardFormDto;
import sangeon.board.controller.dto.BoardViewDto;
import sangeon.board.entity.board.Board;
import sangeon.board.entity.member.Member;
import sangeon.board.repository.BoardRepository;
import sangeon.board.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<BoardViewDto> getBoardList() {
        List<BoardViewDto> boards = new ArrayList<>();

        for(Board board : boardRepository.findAll()){
            boards.add(BoardViewDto.of(board));
        }
        return boards;
    }

    @Override
    public BoardViewDto getDetails(Long id) {
        Optional<Board> board = boardRepository.findById(id);

        if(board.isEmpty()){
            return BoardViewDto.builder().build();
        }

        int hits = board.get().getHits();
        board.get().setHits(hits + 1);

        return BoardViewDto.of(board.get());

    }


    @Override
    public void writeBoard(BoardFormDto boardFormDto) {
        Member member = memberRepository.findOneByEmail(boardFormDto.getEmail()).get();
        Board board = boardFormDto.toEntity();
        board.setMember(member);
        boardRepository.save(board);
    }

    @Override
    public Boolean deleteBoard(HttpServletRequest request, Long id) {
        Optional<Board> board = boardRepository.findById(id);

        if(!checkingAccess(request, board)){
            return false; //삭제 실패
        };

        boardRepository.delete(board.get());

        return true; //삭제 성공;
    }

    private Boolean checkingAccess(HttpServletRequest request, Optional<Board> board) {
        HttpSession session = request.getSession();
        SessionMember member = (SessionMember) session.getAttribute("member");

        if(board.isEmpty()) {
            return false;
        }
        if(member == null) {
            return false;
        }


        if(!board.get().getMember().getEmail().equals(member.getEmail())){
            return false;
        }

        return true;
    }
}
