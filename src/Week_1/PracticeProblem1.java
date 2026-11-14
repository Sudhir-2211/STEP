package Week1;

import java.util.Random;

/**
 * Practice Problem 1: Rock-Paper-Scissors Game
 *
 * TASK:
 * Simulate several rounds of Rock-Paper-Scissors between the player and the computer.
 * Record the outcome of each round and print a final scoreboard summarizing wins,
 * losses, draws, and the player's win percentage.
 */
public class PracticeProblem1 {

    public static final String[] MOVES = {"Rock", "Paper", "Scissors"};

    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }
        if ((playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
            (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
            (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"))) {
            return "Player Wins";
        }
        return "Computer Wins";
    }

    public static void main(String[] args) {
        String[] samplePlayerMoves = {"Rock", "Paper", "Scissors", "Rock", "Paper"};
        String[] sampleComputerMoves = {"Scissors", "Paper", "Rock", "Scissors", "Scissors"};

        int wins = 0;
        int losses = 0;
        int draws = 0;

        System.out.printf("%-8s | %-12s | %-14s | %-14s%n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("---------------------------------------------------------");

        for (int i = 0; i < samplePlayerMoves.length; i++) {
            String pMove = samplePlayerMoves[i];
            String cMove = sampleComputerMoves[i];
            String result = playRound(pMove, cMove);

            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }

            System.out.printf("%-8d | %-12s | %-14s | %-14s%n", (i + 1), pMove, cMove, result);
        }

        double winPct = (wins * 100.0) / samplePlayerMoves.length;
        System.out.println("---------------------------------------------------------");
        System.out.printf("Final Summary: Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
                wins, losses, draws, winPct);
    }
}
