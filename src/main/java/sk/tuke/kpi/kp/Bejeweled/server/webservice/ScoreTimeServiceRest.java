package sk.tuke.kpi.kp.Bejeweled.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.Bejeweled.entity.ScoreTime;
import sk.tuke.kpi.kp.Bejeweled.service.ScoreTimeService;

import java.util.List;

@RestController
@RequestMapping("/api/scoreTime")
public class ScoreTimeServiceRest {
    @Autowired
    private ScoreTimeService scoreTimeService;

    @GetMapping("/{game}")
    public List<ScoreTime> getTopScoresByTime(@PathVariable String game) {
        return scoreTimeService.getTopScoresByTime(game);
    }

    @PostMapping
    public void addScore(@RequestBody ScoreTime scoreTime) {
        scoreTimeService.addScore(scoreTime);
    }
}
