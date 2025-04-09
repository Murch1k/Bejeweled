package sk.tuke.kpi.kp.Bejeweled.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private Player player;

    @BeforeEach
    public void setup() {
        player = new Player("TestPlayer");
        board = new Board(8, 8, player);
    }

    @Test
    public void testValidSwapCreatesMatch() throws Exception {
        Field gridField = Board.class.getDeclaredField("grid");
        gridField.setAccessible(true);
        Gem[][] grid = (Gem[][]) gridField.get(board);

        Gem red = new Gem("Normal", "Red");
        grid[0][0] = red;
        grid[0][1] = red;
        grid[0][2] = red;

        List<List<int[]>> matches = board.findMatches();

        assertFalse(matches.isEmpty(), "There must be at least one match");
    }

    @Test
    public void testInvalidSwap() {
        boolean result = board.swapGems(0, 0, 2, 2);
        assertFalse(result, "You can't swap non-neighboring stones");
    }

    @Test
    public void testRefillBoardFillsEmptySpaces() throws Exception {
        Field gridField = Board.class.getDeclaredField("grid");
        gridField.setAccessible(true);
        Gem[][] grid = (Gem[][]) gridField.get(board);

        grid[0][0] = null;
        grid[1][0] = null;

        board.refillBoard();

        assertNotNull(grid[0][0]);
        assertNotNull(grid[1][0]);
    }

    @Test
    public void testCheckGameOverFalseOnStart() {
        assertFalse(board.checkGameOver(), "There should be moves at the beginning of the game");
    }
}
