package sk.tuke.kpi.kp.Bejeweled.Game;

import java.util.Scanner;

public class Menu {
    public static int showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice == -1) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║           BEJEWELED            ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║ Select a game mode:            ║");
            System.out.println("║                                ║");
            System.out.println("║   1. Normal game               ║");
            System.out.println("║   2. Game of Time              ║");
            System.out.println("║   3. Rules of the game         ║");
            System.out.println("║   4. Exit                      ║");
            System.out.println("║                                ║");
            System.out.println("║                                ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.print("Your choice: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    printRules();
                    break;
                case "4":
                    System.out.println("I'll see you later. 👋");
                    System.exit(0);
                default:
                    System.out.println("⛔ Invalid entry! Try again.\n");
            }
        }

        return 0;
    }
    public static int getTimeLimitFromUser() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║ Select the time of the game:   ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║                                ║");
            System.out.println("║   1. 5 minute                  ║");
            System.out.println("║   2. 10 minute                 ║");
            System.out.println("║   3. 15 minute                 ║");
            System.out.println("║                                ║");
            System.out.println("║   4.Back to main  menu         ║");
            System.out.println("║                                ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.print("Your choice: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1": return 5 * 60;
                case "2": return 10 * 60;
                case "3": return 15 * 60;
                case "4": return -1;
                default:
                    System.out.println("⛔ Invalid entry. Try again.");
            }
        }
    }

    public static void printRules() {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║ Rules of the game:                                ║");
        System.out.println("╠═══════════════════════════════════════════════════╣");
        System.out.println("║- Swap neighboring gems to make 3+ identical ones. ║ ");
        System.out.println("║- You get points for each match.                   ║ ");
        System.out.println("║- Combos of 4 and 5 gems give bonus points.        ║ ");
        System.out.println("║- In the 'Game of Time' you are limited by a timer!║ ");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println("Press Enter to return to the menu...");
        new Scanner(System.in).nextLine();
    }
}
