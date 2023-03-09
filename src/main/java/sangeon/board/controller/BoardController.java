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
import sangeon.board.Service.dto.BoardDto;
import sangeon.board.controller.dto.BoardViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/list")
    public String list(Model model){

        model.addAttribute("boards", boardService.getBoardList());
        return "/board/list";
    }

    @GetMapping ("/member/board/write")
    public String writeForm(Model model){
        model.addAttribute("board", new BoardDto());
        return "/board/write";
    }

    @PostMapping("/member/board/write")
    public String write(@Validated @ModelAttribute("board") BoardDto boardDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/board/write";
        }

        boardService.saveBoard(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping ("/member/board/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response){
        BoardViewDto details = boardService.getPost(id);


        model.addAttribute("board", details);

        return "/board/edit";
    }

    @PostMapping("/member/board/{id}/edit")
    public String edit(@Validated @ModelAttribute("board") BoardDto boardFormDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/board/edit";
        }

        return "/board/details";
    }


    @RequestMapping("/board/{id}/details")
    public String details(@PathVariable Long id, Model model) {

        BoardViewDto board = boardService.getPost(id);
        model.addAttribute("board", board);
        return "/board/details";
    }

    @GetMapping("/member/board/{id}/delete")
    public String delete(@PathVariable Long id, HttpServletRequest request) {

        Boolean delete = boardService.deletePost(request, id);

        return "redirect:/board/list";
    }


}
