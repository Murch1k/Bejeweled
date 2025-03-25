package sk.tuke.kpi.kp.Bejeweled.Game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testInitialScoreIsZero() {
        Player player = new Player();
        assertEquals(0, player.getScore());
    }

    @Test
    public void testAddScore() {
        Player player = new Player();
        player.addScore(50);
        assertEquals(50, player.getScore());
    }
}
