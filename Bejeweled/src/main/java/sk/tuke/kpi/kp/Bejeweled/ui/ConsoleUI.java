package sk.tuke.kpi.kp.Bejeweled.ui;

import sk.tuke.kpi.kp.Bejeweled.Enums.GameState;
import sk.tuke.kpi.kp.Bejeweled.Game.Board;
import sk.tuke.kpi.kp.Bejeweled.Game.Menu;
import sk.tuke.kpi.kp.Bejeweled.Game.Player;
import sk.tuke.kpi.kp.Bejeweled.entity.Comment;
import sk.tuke.kpi.kp.Bejeweled.entity.Rating;
import sk.tuke.kpi.kp.Bejeweled.entity.Score;
import sk.tuke.kpi.kp.Bejeweled.service.CommentServiceJDBC;
import sk.tuke.kpi.kp.Bejeweled.service.RatingServiceJDBC;
import sk.tuke.kpi.kp.Bejeweled.service.ScoreServiceJDBC;
import sk.tuke.kpi.kp.Bejeweled.service.ScoreTimeServiceJDBC;

import java.util.Date;
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

    public void startGame(){
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
        public void runNormalGame(Board board, Player player, ConsoleUI ui){
            printBoard(board);
            printGameState(board.getState());
            printScore(player);
            while (board.checkPossibleMoves()) {
                int[] move = getPlayerMove();
                if (move == null) {
                    System.out.println("👋 Exit to the menu. The game is finished.");
                    break;
                }
                if (board.swapGems(move[0], move[1], move[2], move[3])) {
                    System.out.println("The move is done!");
                }
                printBoard(board);
                printGameState(board.getState());
                printScore(player);
            }if(!board.checkPossibleMoves()) {
                board.setState(GameState.LOSS);
                System.out.println("No moves available. Game over!");
            }

            ScoreServiceJDBC scoreService = new ScoreServiceJDBC();
            Score score = new Score("Bejeweled", player.getName(), player.getScore(), new Date());
            scoreService.addScore(score);
            System.out.println("🏆 The result is saved to the database!");
            System.out.print("💬 Leave a comment about the game: ");
            Scanner scanner = new Scanner(System.in);
            String commentText = scanner.nextLine();

            CommentServiceJDBC commentService = new CommentServiceJDBC();
            Comment comment = new Comment("Bejeweled", player.getName(), commentText, new Date());
            commentService.addComment(comment);
            System.out.println("✅ Comment saved!");

            int rating = getRating();
            new RatingServiceJDBC().setRating(new Rating("Bejeweled", player.getName(), rating, new Date()));
            System.out.println("✅ Rating saved!");
        }
        public void runTimeGame(Board board, Player player, ConsoleUI ui, int secondsLimit) {
            long startTime = System.currentTimeMillis();
            long endTime = startTime + secondsLimit * 1000L;
            printBoard(board);
            printGameState(board.getState());
            printScore(player);
            while (System.currentTimeMillis() < endTime && board.checkPossibleMoves()) {
                long timeLeft = (endTime - System.currentTimeMillis()) / 1000;
                System.out.println("⏳ There's time left: " + timeLeft + " sec");

                int[] move = getPlayerMove();
                if (System.currentTimeMillis() >= endTime) break;
                if (board.swapGems(move[0], move[1], move[2], move[3])) {
                    System.out.println("The move is done!");
                }
                printBoard(board);
                printScore(player);
                printGameState(board.getState());
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n🕒 Time's up! Your scores: " + player.getScore());
            ScoreTimeServiceJDBC scoreTimeService = new ScoreTimeServiceJDBC(secondsLimit / 60);
            Score score = new Score("Bejeweled", player.getName(), player.getScore(), new Date());
            scoreTimeService.addScore(score);
            System.out.println("⏱️ Timed score saved to database!");
            System.out.print("⭐ Rate the game from 1 to 5: ");

            int rating = getRating();
            new RatingServiceJDBC().setRating(new Rating("Bejeweled", player.getName(), rating, new Date()));
            System.out.println("✅ Rating saved!");
        }
        public int getRating() {
            System.out.print("⭐ Rate the game from 1 to 5: ");
            while (true) {
                try {
                    int rating = Integer.parseInt(scanner.nextLine());
                    if (rating >= 1 && rating <= 5) return rating;
                    else System.out.print("Please enter a number between 1 and 5: ");
                } catch (NumberFormatException e) {
                    System.out.print("Invalid number. Try again: ");
                }
            }

        }
    public void showAvailableMoves(Board board) {
        System.out.println("Checking available moves...");
    }
}
