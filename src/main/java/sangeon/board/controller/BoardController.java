package sangeon.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sangeon.board.Service.board.BoardService;
import sangeon.board.Service.dto.BoardFormDto;
import sangeon.board.controller.dto.BoardViewDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/list")
    public String list(Model model){

        List<BoardViewDto> boards = boardService.getBoardList();
        model.addAttribute("boards", boards);

        return "/board/list";
    }

    @GetMapping ("/member/board/write")
    public String writeForm(Model model){
        model.addAttribute("board", new BoardFormDto());
        return "/board/writeForm";
    }

    @PostMapping("/member/board/write")
    public String write(@Validated @ModelAttribute("board") BoardFormDto boardFormDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/board/writeForm";
        }

        boardService.writeBoard(boardFormDto);
        return "redirect:/board/list";
    }

    @RequestMapping("/board/{id}/details")
    public String details(@PathVariable Long id, Model model) {

        BoardViewDto board = boardService.getDetails(id);
        model.addAttribute("board", board);
        return "/board/details";
    }

    @GetMapping("/member/board/{id}/delete")
    public String delete(@PathVariable Long id, HttpServletRequest request) {

        Boolean delete = boardService.deleteBoard(request, id);

        if(delete == false) {

        }

        return "redirect:/board/list";
    }
}
