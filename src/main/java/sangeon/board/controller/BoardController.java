package sangeon.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sangeon.board.OAuth.SessionMember;
import sangeon.board.Service.board.BoardService;
import sangeon.board.entity.board.Board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/list")
    public String list(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        SessionMember member = (SessionMember) session.getAttribute("member");
        model.addAttribute("member", member);

        List<Board> boards = boardService.getBoardList();
        model.addAttribute("boards", boards);

        return "/board/list";
    }

    @GetMapping ("/member/board/write")
    public String writeForm(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        SessionMember member = (SessionMember) session.getAttribute("member");
        model.addAttribute("member", member);

        return "/board/writeForm";
    }

    @PostMapping("/member/board/write")
    public String write(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        SessionMember member = (SessionMember) session.getAttribute("member");
        model.addAttribute("member", member);

        return "redirect:/";
    }
}
