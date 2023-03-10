package sangeon.board.Service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import sangeon.board.OAuth.SessionMember;
import sangeon.board.controller.dto.*;
import sangeon.board.entity.board.Board;
import sangeon.board.entity.member.Address;
import sangeon.board.entity.member.Gender;
import sangeon.board.entity.member.Member;
import sangeon.board.repository.BoardCustomRepository;
import sangeon.board.repository.BoardRepository;
import sangeon.board.repository.MemberRepository;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardCustomRepository boardCustomRepository;
    private final MemberRepository memberRepository;


    @Override
    public PagingResponse<BoardDto> getBoardList(SearchDto param) {

        Long count = boardCustomRepository.recordCount(param);
        System.out.println("++++++++++++++++++++++++++++++++++++++++ "+count +" ++++++++++++++++++++++++++++++++++++++++");
        if(count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, param);

        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardCustomRepository.findAll(param)) {
            boardDtos.add(BoardDto.of(board));
        }

        return new PagingResponse<>(boardDtos, pagination);
    }

    @Override
    public Long saveBoard(HttpSession session, BoardDto boardDto) {

        SessionMember member = (SessionMember) session.getAttribute("member");

        if(member == null || !member.getEmail().equals(boardDto.getEmail())) {
            throw new AccessDeniedException("로그인 아이디와 게시글 작성자 아이디는 같아야 합니다.");
        }

        Board board = boardDto.toEntity();
        board.setMember(memberRepository.findOneByEmail(member.getEmail()).get());

        return boardRepository.save(board).getId();
    }

    @Override
    public BoardDto getBoard(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        return BoardDto.of(boardWrapper.orElseThrow(
                () -> new EmptyResultDataAccessException("해당 게시글에 대한 권한이 없습니다.",1)));
    }

    @Override
    public void update(HttpSession session, Long id, BoardUpdateDto boardUpdateDto) {
        if(!isSessionMemberEquBoardMember(session, id)) {
            throw new AccessDeniedException("해당 게시글에 대한 권한이 없습니다.");
        }

        Board board = boardRepository.findById(id).get();
        board.setTitle(boardUpdateDto.getTitle());
        board.setContent(boardUpdateDto.getContent());
    }

    @Override
    public void delete(HttpSession session, Long id) {
        if(!isSessionMemberEquBoardMember(session, id)) {
            throw new AccessDeniedException("해당 게시글에 대한 권한이 없습니다.");
        }

        boardRepository.delete(boardRepository.findById(id).get());
    }

    @Override
    public Boolean isSessionMemberEquBoardMember(HttpSession session, Long board_id){
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");

        Optional<Board> boardWrapper = boardRepository.findById(board_id);
        Board board = boardWrapper.orElseThrow(
                () -> new EmptyResultDataAccessException("해당 게시글이 존재하지 않습니다.", 1));

        if(sessionMember == null || !board.getMember().getEmail().equals(sessionMember.getEmail())){
            return false;
        }
        return true;
    }
}
