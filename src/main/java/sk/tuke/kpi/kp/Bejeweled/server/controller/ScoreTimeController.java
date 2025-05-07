package sk.tuke.kpi.kp.Bejeweled.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ScoreTimeController {

    @RequestMapping("/scoretime")
    public String scoreTime(
            @RequestParam(value = "player", required = false) String player,
            @RequestParam(value = "points", required = false) Integer points,
            @RequestParam(value = "time", required = false) String time,
            Model model) {

        model.addAttribute("player", player);
        model.addAttribute("points", points);
        model.addAttribute("time", time);

        return "scoretime";
    }
}
