package org.example;

import org.example.Game.Board;
import org.example.Game.Menu;
import org.example.Game.Player;
import org.example.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        int gameMode = Menu.showMenu();
        Player player = new Player();
        Board board = new Board(8, 8, player);
        ConsoleUI ui = new ConsoleUI();
//        ui.printBoard(board);
//        ui.printGameState(board.getState());
//        while (board.checkPossibleMoves()) {
//            int[] move = ui.getPlayerMove();
//            if (board.swapGems(move[0], move[1], move[2], move[3])) {
//                System.out.println("Swap successful!");
//            }
//            ui.printBoard(board);
//            ui.printScore(player);
//        }
//
//        System.out.println("No more possible moves. Game over!");
        if (gameMode == 1) {
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
            System.out.println("No moves available. Game over!");
        } else if (gameMode == 2) {
            int secondsLimit = Menu.getTimeLimitFromUser();
            long startTime = System.currentTimeMillis();
            long endTime = startTime + secondsLimit * 1000L;
            ui.printBoard(board);
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
            }

            System.out.println("\n🕒 Time's up! Your scores: " + player.getScore());
        }
    }
}