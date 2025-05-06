package sk.tuke.kpi.kp.Bejeweled.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CommentsController {

    @GetMapping("/comment")
    public String showComments(
            @RequestParam(value = "player", required = false) String player,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "timestamp", required = false) String timestamp,
            Model model) {

        model.addAttribute("player", player);
        model.addAttribute("comment", comment);
        model.addAttribute("timestamp", timestamp);

        return "comments";
    }
}

