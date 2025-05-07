package sk.tuke.kpi.kp.Bejeweled.Game;

import org.junit.jupiter.api.*;
import sk.tuke.kpi.kp.Bejeweled.entity.Score;
import sk.tuke.kpi.kp.Bejeweled.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceJDBCTest {

    private ScoreServiceJDBC service;

    @BeforeEach
    public void setup() {
        service = new ScoreServiceJDBC();
        service.reset();
    }

    @Test
    public void testAddAndGetTopScores() {
        Score score = new Score("Bejeweled", "Tester", 123, new Date());
        service.addScore(score);

        List<Score> scores = service.getTopScores("Bejeweled");
        assertEquals(1, scores.size());
        assertEquals("Tester", scores.get(0).getPlayer());
    }

    @Test
    public void testReset() {
        service.addScore(new Score("Bejeweled", "Tester", 100, new Date()));
        service.reset();
        List<Score> scores = service.getTopScores("Bejeweled");
        assertTrue(scores.isEmpty());
    }
}