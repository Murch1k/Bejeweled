package sk.tuke.kpi.kp.Bejeweled.Game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {

    @Test
    public void testScoreFor3Gems() {
        Score score = new Score();
        assertEquals(30, score.calculateScore(3));
    }

    @Test
    public void testScoreFor4Gems() {
        Score score = new Score();
        assertEquals(60, score.calculateScore(4));
    }

    @Test
    public void testScoreFor5Gems() {
        Score score = new Score();
        assertEquals(100, score.calculateScore(5));
    }
}