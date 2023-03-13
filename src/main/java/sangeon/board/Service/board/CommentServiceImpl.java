package sangeon.board.Service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import sangeon.board.OAuth.SessionMember;
import sangeon.board.controller.dto.CommentDto;
import sangeon.board.entity.board.Comment;
import sangeon.board.repository.BoardRepository;
import sangeon.board.repository.CommentRepository;
import sangeon.board.repository.MemberRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public Long saveComment(HttpSession session, CommentDto commentDto) {
        SessionMember member = (SessionMember) session.getAttribute("member");

        if(member == null || !member.getEmail().equals(commentDto.getEmail())) {
            throw new AccessDeniedException("로그인 아이디와 게시글 작성자 아이디는 같아야 합니다.");
        }

        return commentRepository.save(Comment.builder()
                .board(boardRepository.findById(commentDto.getBoardId()).get())
                .member(memberRepository.findOneByEmail(commentDto.getEmail()).get())
                .content(commentDto.getContent())
                .build()).getId();

    }

    @Override
    public List<CommentDto> getComments() {
        return null;
    }

    @Override
    public List<CommentDto> getChildComment(Long parentId) {
        return null;
    }

    @Override
    public Boolean isSessionMemberEquBoardMember(HttpSession session, Long comment_id){
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");

        Optional<Comment> commentWrapper = commentRepository.findById(comment_id);
        Comment Comment = commentWrapper.orElseThrow(
                () -> new EmptyResultDataAccessException("해당  존재하지 않습니다.", 1));

        if(sessionMember == null || !Comment.getMember().getEmail().equals(sessionMember.getEmail())){
            return false;
        }
        return true;
    }
}
