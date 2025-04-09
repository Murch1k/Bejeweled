package sk.tuke.kpi.kp.Bejeweled.Game;

import org.junit.jupiter.api.*;
import sk.tuke.kpi.kp.Bejeweled.entity.ScoreTime;
import sk.tuke.kpi.kp.Bejeweled.service.ScoreTimeServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreTimeServiceJDBCTest {

    private ScoreTimeServiceJDBC scoreService;

    @BeforeEach
    void setUp() {
        scoreService = new ScoreTimeServiceJDBC(5);
        scoreService.reset();
    }

    @Test
    void testAddAndGetScore() {
        ScoreTime score = new ScoreTime("Bejeweled", "TestPlayer", 150,  5,  new Date());
        scoreService.addScore(score);

        List<ScoreTime> scores = scoreService.getTopScores("Bejeweled");

        assertEquals(1, scores.size());
        assertEquals("TestPlayer", scores.get(0).getPlayer());
        assertEquals(150, scores.get(0).getPoints());
    }

    @Test
    void testReset() {
        scoreService.addScore(new ScoreTime("Bejeweled", "Someone", 100, 5, new Date()));
        scoreService.reset();
        List<ScoreTime> scores = scoreService.getTopScores("Bejeweled");
        assertTrue(scores.isEmpty());
    }

    @Test
    void testMultipleScoresSortedByPoints() {
        scoreService.addScore(new ScoreTime("Bejeweled", "Player1", 100, 10,  new Date()));
        scoreService.addScore(new ScoreTime("Bejeweled", "Player2", 300, 15, new Date()));
        scoreService.addScore(new ScoreTime("Bejeweled", "Player3", 200, 5,  new Date()));

        List<ScoreTime> scores = scoreService.getTopScores("Bejeweled");

        assertEquals(3, scores.size());
        assertEquals("Player2", scores.get(0).getPlayer());
        assertEquals("Player3", scores.get(1).getPlayer());
        assertEquals("Player1", scores.get(2).getPlayer());
    }
}
