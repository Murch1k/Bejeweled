package sk.tuke.kpi.kp.Bejeweled.Game;

import sk.tuke.kpi.kp.Bejeweled.Enums.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int columns;
    private int rows;
    private Gem[][] grid;
    private GameState state;
    private Player player;
    private Score score;
    private static final String[] COLORS = {"Red", "Blue", "Green", "Yellow", "Purple"};

    public Board(int rows, int columns, Player player) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new Gem[rows][columns];
        this.state = GameState.RUNNING;
        this.player = player;
        score = new Score();
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Gem newGem;
                do {
                    newGem = generateNewGem(i, j);
                    grid[i][j] = newGem;
                } while ((j >= 2 && grid[i][j - 1].getColor().equals(newGem.getColor()) && grid[i][j - 2].getColor().equals(newGem.getColor())) ||
                        (i >= 2 && grid[i - 1][j].getColor().equals(newGem.getColor()) && grid[i - 2][j].getColor().equals(newGem.getColor())));
            }
        }
    }

    public boolean swapGems(int x1, int y1, int x2, int y2) {
        if (!isValidSwap(x1, y1, x2, y2)) {
            System.out.println("Invalid move! Gems are not adjacent.");
            return false;
        }

        Gem temp1 = grid[x1][y1];
        Gem temp2 = grid[x2][y2];
        grid[x1][y1] = temp2;
        grid[x2][y2] = temp1;

        List<List<int[]>> matches = findMatches();
        if (matches.isEmpty()) {
            grid[x1][y1] = temp1;
            grid[x2][y2] = temp2;
            System.out.println("Invalid move! No match found.");
            return false;
        }

        removeMatchedGems(matches, player);
        while (refillBoard()) {
            matches = findMatches();
            if (!matches.isEmpty()) {
                removeMatchedGems(matches, player);
            }
        }

        return true;
    }

    public void removeMatchedGems(List<List<int[]>> matches, Player player) {
        for (List<int[]> match : matches) {
            for (int[] pos : match) {
                grid[pos[0]][pos[1]] = null;
            }
            int points = score.calculateScore(match.size());
            player.addScore(points);
        }
    }

    public boolean isValidSwap(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1;
    }

    public List<List<int[]>> findMatches() {
        List<List<int[]>> matches = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 2; j++) {
                if(grid[i][j] == null) continue;
                if (grid[i][j].isMatch(grid[i][j + 1]) &&
                        grid[i][j].isMatch(grid[i][j + 2])) {

                    List<int[]> match = new ArrayList<>();
                    match.add(new int[]{i, j});
                    match.add(new int[]{i, j + 1});
                    match.add(new int[]{i, j + 2});

                    int k = j + 3;
                    while (k < columns && grid[i][k].isMatch(grid[i][j])) {
                        match.add(new int[]{i, k});
                        k++;
                    }

                    matches.add(match);
                    j = k - 1;
                }
            }
        }

        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows - 2; i++) {
                if (grid[i][j] != null &&
                        grid[i][j].isMatch(grid[i + 1][j]) &&
                        grid[i][j].isMatch(grid[i + 2][j])) {

                    List<int[]> match = new ArrayList<>();
                    match.add(new int[]{i, j});
                    match.add(new int[]{i + 1, j});
                    match.add(new int[]{i + 2, j});

                    int k = i + 3;
                    while (k < rows && grid[k][j].isMatch(grid[i][j])) {
                        match.add(new int[]{k, j});
                        k++;
                    }

                    matches.add(match);
                    i = k - 1;
                }
            }
        }
        return matches;
    }

    public boolean refillBoard() {
        boolean refilled = false;

        for (int j = 0; j < columns; j++) {
            int emptyCount = 0;
            for (int i = rows - 1; i >= 0; i--) {
                if (grid[i][j] == null) {
                    emptyCount++;
                } else if (emptyCount > 0) {
                    grid[i + emptyCount][j] = grid[i][j];
                    grid[i][j] = null;
                }
            }

            for (int i = 0; i < emptyCount; i++) {
                grid[i][j] = generateNewGem(i, j);
                refilled = true;
            }
        }
        return refilled;
    }

    public Gem generateNewGem(int row, int column) {
        return new Gem("Normal", COLORS[new Random().nextInt(COLORS.length)]);
    }

    public boolean checkGameOver() {
        return !checkPossibleMoves();
    }

    public boolean checkPossibleMoves() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j < columns - 1 && isValidSwap(i, j, i, j + 1)) return true;
                if (i < rows - 1 && isValidSwap(i, j, i + 1, j)) return true;
            }
        }
        return false;
    }

    public boolean hasAnyValidMatchAfterSwap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j < columns - 1) {
                    swap(i, j, i, j + 1);
                    if (!findMatches().isEmpty()) {
                        swap(i, j, i, j + 1);
                        return true;
                    }
                    swap(i, j, i, j + 1);
                }

                if (i < rows - 1) {
                    swap(i, j, i + 1, j);
                    if (!findMatches().isEmpty()) {
                        swap(i, j, i + 1, j);
                        return true;
                    }
                    swap(i, j, i + 1, j);
                }
            }
        }
        return false;
    }

    public void swap(int x1, int y1, int x2, int y2) {
        Gem temp = grid[x1][y1];
        grid[x1][y1] = grid[x2][y2];
        grid[x2][y2] = temp;
    }

    public GameState getState() {
        return state;
    }

    public void printBoard() {
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String BLUE = "\u001B[34m";
        final String GREEN = "\u001B[32m";
        final String YELLOW = "\u001B[33m";
        final String PURPLE = "\u001B[35m";

        System.out.print("  ");
        for (int j = 0; j < columns; j++) {
            System.out.print(j + "  ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            char rowLabel = (char) ('A' + i);
            System.out.print(rowLabel + " ");

            for (int j = 0; j < columns; j++) {
                String color = grid[i][j].getColor();
                String symbol = grid[i][j].getColor().charAt(0) + "  ";
                switch (color) {
                    case "Red": System.out.print(RED + symbol + RESET); break;
                    case "Blue": System.out.print(BLUE + symbol + RESET); break;
                    case "Green": System.out.print(GREEN + symbol + RESET); break;
                    case "Yellow": System.out.print(YELLOW + symbol + RESET); break;
                    case "Purple": System.out.print(PURPLE + symbol + RESET); break;
                    default: System.out.print(symbol);
                }
            }
            System.out.println();
        }
    }

    public void regenerateBoard() {
        initializeBoard();
        System.out.println("No more moves. The board has been regenerated!");
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Gem[][] getGrid() {
        return grid;
    }

    public Player getPlayer() {
        return player;
    }

    public String renderHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table class='game-board'>");

        for (int i = 0; i < rows; i++) {
            sb.append("<tr>");
            for (int j = 0; j < columns; j++) {
                Gem gem = grid[i][j];
                String color = gem != null ? gem.getColor().toLowerCase() : "empty";
                sb.append("<td class='gem-cell' data-x='").append(i)
                        .append("' data-y='").append(j).append("'>");
                if (!color.equals("empty")) {
                    sb.append("<img src='/images/").append(color)
                            .append(".png' class='gem-img")
                            .append(gem == null ? " removed" : "")
                            .append("'/>");
                }
                sb.append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }


}

