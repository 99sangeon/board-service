package sangeon.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import sangeon.board.Service.board.CommentService;
import sangeon.board.controller.dto.CommentDto;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @RequestMapping("member/comment/write")
    public String write(HttpServletRequest request, @ModelAttribute CommentDto commentDto){

        commentService.saveComment(request.getSession(), commentDto);
        return "redirect:/board/" + commentDto.getBoardId() + "/details";
    }
}
