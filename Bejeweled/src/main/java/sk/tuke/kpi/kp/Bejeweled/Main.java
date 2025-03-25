package sk.tuke.kpi.kp.Bejeweled;

import sk.tuke.kpi.kp.Bejeweled.Test.Board;
import sk.tuke.kpi.kp.Bejeweled.Test.Menu;
import sk.tuke.kpi.kp.Bejeweled.Test.Player;
import sk.tuke.kpi.kp.Bejeweled.ui.ConsoleUI;
import sk.tuke.kpi.kp.Bejeweled.Enums.GameState;

public class Main {
    public static void main(String[] args) {
        while(true) {
            int gameMode = Menu.showMenu();
            Player player = new Player();
            Board board = new Board(8, 8, player);
            ConsoleUI ui = new ConsoleUI();
            if (gameMode == 1) {
                runNormalGame(board, player, ui);
            } else if (gameMode == 2) {
                int secondsLimit = Menu.getTimeLimitFromUser();
                if (secondsLimit == -1) {
                    continue;
                }
                runTimeGame(board, player, ui, secondsLimit);
            }
        }
    }
    public static void runNormalGame(Board board, Player player, ConsoleUI ui){
        ui.printBoard(board);
        ui.printGameState(board.getState());
        ui.printScore(player);
        while (board.checkPossibleMoves()) {
            int[] move = ui.getPlayerMove();
            if (board.swapGems(move[0], move[1], move[2], move[3])) {
                System.out.println("The move is done!");
            }
            ui.printBoard(board);
            ui.printGameState(board.getState());
            ui.printScore(player);
        }
        board.setState(GameState.LOSS);
        System.out.println("No moves available. Game over!");
    }
    public static void runTimeGame(Board board, Player player, ConsoleUI ui, int secondsLimit){
        long startTime = System.currentTimeMillis();
        long endTime = startTime + secondsLimit * 1000L;
        ui.printBoard(board);
        ui.printGameState(board.getState());
        ui.printScore(player);
        while (System.currentTimeMillis() < endTime && board.checkPossibleMoves()) {
            long timeLeft = (endTime - System.currentTimeMillis()) / 1000;
            System.out.println("⏳ There's time left: " + timeLeft + " sec");

            int[] move = ui.getPlayerMove();
            if (System.currentTimeMillis() >= endTime) break;
            if (board.swapGems(move[0], move[1], move[2], move[3])) {
                System.out.println("The move is done!");
            }
            ui.printBoard(board);
            ui.printScore(player);
            ui.printGameState(board.getState());
        }

        System.out.println("\n🕒 Time's up! Your scores: " + player.getScore());
    }
}