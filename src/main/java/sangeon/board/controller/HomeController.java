package sangeon.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sangeon.board.OAuth.SessionMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        SessionMember member = (SessionMember) session.getAttribute("member");
        model.addAttribute("member", member);
        return "index";
    }

}
