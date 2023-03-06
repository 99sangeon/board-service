package sangeon.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sangeon.board.Service.board.BoardService;
import sangeon.board.Service.dto.BoardFormDto;
import sangeon.board.controller.dto.BoardListDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/list")
    public String list(Model model){

        List<BoardListDto> boards = boardService.getBoardList();
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
}
