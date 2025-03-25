package sk.tuke.kpi.kp.Bejeweled;

import sk.tuke.kpi.kp.Bejeweled.Game.Board;
import sk.tuke.kpi.kp.Bejeweled.Game.Menu;
import sk.tuke.kpi.kp.Bejeweled.Game.Player;
import sk.tuke.kpi.kp.Bejeweled.entity.Comment;
import sk.tuke.kpi.kp.Bejeweled.entity.Score;
import sk.tuke.kpi.kp.Bejeweled.service.CommentServiceJDBC;
import sk.tuke.kpi.kp.Bejeweled.service.ScoreServiceJDBC;
import sk.tuke.kpi.kp.Bejeweled.service.ScoreTimeServiceJDBC;
import sk.tuke.kpi.kp.Bejeweled.ui.ConsoleUI;
import sk.tuke.kpi.kp.Bejeweled.Enums.GameState;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            int gameMode = Menu.showMenu();
            if (gameMode == 1) {
                System.out.print("Enter your nickname: ");
                String playerName = scanner.nextLine();
                Player player = new Player(playerName);
                Board board = new Board(8, 8, player);
                ConsoleUI ui = new ConsoleUI();
                runNormalGame(board, player, ui);
            } else if (gameMode == 2) {
                int secondsLimit = Menu.getTimeLimitFromUser();
                if (secondsLimit == -1) {
                    continue;
                }
                System.out.print("Enter your nickname: ");
                String playerName = scanner.nextLine();
                Player player = new Player(playerName);
                Board board = new Board(8, 8, player);
                ConsoleUI ui = new ConsoleUI();
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
            if (move == null) {
                System.out.println("👋 Exit to the menu. The game is finished.");
                break;
            }
            if (board.swapGems(move[0], move[1], move[2], move[3])) {
                System.out.println("The move is done!");
            }
            ui.printBoard(board);
            ui.printGameState(board.getState());
            ui.printScore(player);
        }if(!board.checkPossibleMoves()) {
            board.setState(GameState.LOSS);
            System.out.println("No moves available. Game over!");
        }

        ScoreServiceJDBC scoreService = new ScoreServiceJDBC();
        Score score = new Score("Bejeweled", player.getName(), player.getScore(), new Date());
        scoreService.addScore(score);
        System.out.println("🏆 The result is saved to the database!");
        System.out.print("💬 Leave a comment about the game: ");
        Scanner scanner = new Scanner(System.in) ;
        String commentText = scanner.nextLine();

        CommentServiceJDBC commentService = new CommentServiceJDBC();
        Comment comment = new Comment("Bejeweled", player.getName(), commentText, new Date());
        commentService.addComment(comment);
        System.out.println("✅ Comment saved!");
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
        ScoreTimeServiceJDBC scoreTimeService = new ScoreTimeServiceJDBC(secondsLimit / 60);
        Score score = new Score("Bejeweled", player.getName(), player.getScore(), new Date());
        scoreTimeService.addScore(score);
        System.out.println("⏱️ Timed score saved to database!");
    }
}