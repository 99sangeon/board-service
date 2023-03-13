package sangeon.board.Service.board;

import sangeon.board.controller.dto.CommentDto;
import sangeon.board.entity.board.Comment;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CommentService {

    public Long saveComment(HttpSession session , CommentDto commentDto);

    public List<CommentDto> getComments();

    public List<CommentDto> getChildComment(Long parentId);

    Boolean isSessionMemberEquBoardMember(HttpSession session, Long comment_id);
}
