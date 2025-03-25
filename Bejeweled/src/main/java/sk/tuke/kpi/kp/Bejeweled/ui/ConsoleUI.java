package sk.tuke.kpi.kp.Bejeweled.ui;

import sk.tuke.kpi.kp.Bejeweled.Enums.GameState;
import sk.tuke.kpi.kp.Bejeweled.Game.Board;
import sk.tuke.kpi.kp.Bejeweled.Game.Player;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void printBoard(Board board) {
        board.printBoard();
    }

    public void printScore(Player player) {
        System.out.println("Player Score: " + player.getScore());
    }

    public void printGameState(GameState state) {
        System.out.println("Game State: " + state);
    }

    public int[] getPlayerMove() {
        System.out.println("Enter swap coordinates (e.g., A1 B2): ");
        String input = scanner.nextLine().toUpperCase().replaceAll("\\s+", ""); // Убираем пробелы

        if (input.equalsIgnoreCase("EXIT")) {
            return null;
        }

        if (input.length() != 4) {
            System.out.println("Invalid input format! Use format: A1B2");
            return getPlayerMove();
        }

        int row1 = input.charAt(0) - 'A';
        int col1 = Character.getNumericValue(input.charAt(1));
        int row2 = input.charAt(2) - 'A';
        int col2 = Character.getNumericValue(input.charAt(3));

        return new int[]{row1, col1, row2, col2};
    }

    public void showAvailableMoves(Board board) {
        System.out.println("Checking available moves...");
    }
}
