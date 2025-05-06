package sk.tuke.kpi.kp.Bejeweled.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.kpi.kp.Bejeweled.Game.Board;
import sk.tuke.kpi.kp.Bejeweled.Game.Player;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/gameTime")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GameTimeController {

    private Board board = new Board(8, 8, new Player("Player"));

    @GetMapping("/timed")
    public String showTimed(Model model) {
        board = new Board(8, 8, new Player("Player"));
        model.addAttribute("boardHtml", board.renderHtml());
        model.addAttribute("score", board.getPlayer().getScore());
        return "gameTime";
    }

    @PostMapping("/move")
    @ResponseBody
    public Map<String, Object> move(@RequestParam int x1, @RequestParam int y1,
                                    @RequestParam int x2, @RequestParam int y2) {
        board.swapGems(x1, y1, x2, y2);
        Map<String, Object> result = new HashMap<>();
        result.put("html", board.renderHtml());
        result.put("score", board.getPlayer().getScore());
        return result;
    }
}
