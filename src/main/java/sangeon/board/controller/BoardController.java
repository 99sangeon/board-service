package sangeon.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sangeon.board.OAuth.SessionMember;
import sangeon.board.Service.board.BoardService;
import sangeon.board.controller.dto.BoardDto;
import sangeon.board.controller.dto.BoardUpdateDto;
import sangeon.board.controller.dto.SearchDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/list")
    public String list(@ModelAttribute("params")SearchDto params, Model model){
        model.addAttribute("pagingResponse", boardService.getBoardList(params));
        return "/board/list";
    }

    @GetMapping ("/member/board/write")
    public String writeForm(Model model){
        model.addAttribute("board", new BoardDto());
        return "/board/write";
    }

    @PostMapping("/member/board/write")
    public String write(HttpServletRequest request,
                        @Validated @ModelAttribute("board") BoardDto boardDto,
                        BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/board/write";
        }
        Long id = boardService.saveBoard(request.getSession(), boardDto);

        return "redirect:/board/" + id + "/details";
    }

    @GetMapping ("/member/board/{id}/edit")
    public String editForm(@PathVariable Long id,
                           Model model,
                           HttpServletRequest request){

        BoardDto boardDto = boardService.getBoard(id);
        SessionMember member = (SessionMember) request.getSession().getAttribute("member");

        if(!member.getEmail().equals(boardDto.getEmail())){
            throw new AccessDeniedException("해당 게시글에 대한 권한이 없습니다.");
        }

        model.addAttribute("board", boardDto);

        return "/board/edit";
    }

    @PostMapping("/member/board/{id}/edit")
    public String edit(HttpServletRequest request,
                       @PathVariable Long id,
                       @Validated @ModelAttribute("board") BoardDto boardDto,
                       BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "/board/edit";
        }


        boardService.update(request.getSession(), id, BoardUpdateDto
                                                            .builder()
                                                            .title(boardDto.getTitle())
                                                            .content(boardDto.getContent())
                                                            .build());

        return "redirect:/board/" + id + "/details";
    }


    @RequestMapping("/board/{id}/details")
    public String details(@PathVariable Long id, Model model) {

        model.addAttribute("board", boardService.getBoard(id));

        return "/board/details";
    }

    @GetMapping("/member/board/{id}/delete")
    public String delete(HttpServletRequest request,@PathVariable Long id) {

        boardService.delete(request.getSession(), id);

        return "redirect:/board/list";
    }


}
