package sk.tuke.kpi.kp.Bejeweled.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class RatingController {

    @GetMapping("/rating")
    public String rating(@RequestParam(value = "player", required = false) String player,
                         @RequestParam(value = "rating", required = false) Integer rating,
                         Model model) {

        model.addAttribute("player", player);
        model.addAttribute("rating", rating);

        return "rating";
    }
}
