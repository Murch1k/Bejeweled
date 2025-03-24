package org.example.Game;

import org.example.Enums.GameState;

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

    public Board(int rows, int columns, Player player) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new Gem[rows][columns];
        this.state = GameState.RUNNING;
        this.player = player;
        score = new Score();
        initializeBoard();
    }

    private void initializeBoard() {
        Random rand = new Random();
        String[] colors = {"Red", "Blue", "Green", "Yellow", "Purple"};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String newColor;
                do {
                    newColor = colors[rand.nextInt(colors.length)];
                } while ((j >= 2 && grid[i][j - 1].getColor().equals(newColor) && grid[i][j - 2].getColor().equals(newColor)) ||
                        (i >= 2 && grid[i - 1][j].getColor().equals(newColor) && grid[i - 2][j].getColor().equals(newColor)));

                grid[i][j] = new Gem("Normal", newColor, false, "");
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

    private void removeMatchedGems(List<List<int[]>> matches, Player player) {
        for (List<int[]> match : matches) {
            for (int[] pos : match) {
                grid[pos[0]][pos[1]] = null;
            }
            int points = score.calculateScore(match.size());
            player.addScore(points);
        }
    }

    private boolean isValidSwap(int x1, int y1, int x2, int y2) {
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
        Random rand = new Random();
        String[] colors = {"Red", "Blue", "Green", "Yellow", "Purple"};

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
                grid[i][j] = new Gem("Normal", colors[rand.nextInt(colors.length)], false, "");
                refilled = true;
            }
        }
        return refilled;
    }

//    public void resetBoard() {
//        initializeBoard();
//        this.score = 0;
//        this.state = GameState.RUNNING;
//    }

    public Gem generateNewGem(int row, int column) {
        String[] colors = {"Red", "Blue", "Green", "Yellow", "Purple"};
        return new Gem("Normal", colors[new Random().nextInt(colors.length)], false, "");
    }

    public boolean checkGameOver() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j < columns - 1 && isValidSwap(i, j, i, j + 1)) return true;
                if (i < rows - 1 && isValidSwap(i, j, i + 1, j)) return true;
            }
        }
        return false;
    }

    public boolean checkPossibleMoves() {
        return true;
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

}
