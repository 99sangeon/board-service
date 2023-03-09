package sangeon.board.Service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import sangeon.board.OAuth.SessionMember;
import sangeon.board.Service.dto.BoardDto;
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


    private Boolean isSessionMemberEquBoardMember(HttpServletRequest request, BoardViewDto board){
        HttpSession session = request.getSession();
        SessionMember member = (SessionMember) session.getAttribute("member");

        if(member == null || !member.getEmail().equals(board.getEmail())) {
            throw new AccessDeniedException("해당 게시글에 대한 권한이 없습니다!");
        }

        return true;
    }

}
