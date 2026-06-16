package DominoGameVer1;

/**
 * Represents the main game logic for the Domino game
 * @author Enrique Mendoza
 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DominoGame {
    // Represent the human player
    private Player human;
    //Represents the computer player
    private Player computer;
    //Represents the pool of undrawn dominos
    private Boneyard boneyard;
    //Represent the current state of the game board
    private GameBoard board;
    // Represent the input of the player
    private Scanner scanner;
    //Player who made the last move
    private Player lastPlayer;

    /**
     * Initialize players, boneyard, game board and scanner.
     */
    public DominoGame() {
        human = new Player("Human");
        computer = new Player("Computer");
        boneyard = new Boneyard();
        board = new GameBoard();
        scanner = new Scanner(System.in);
        lastPlayer = null;
    }
    /**
     * Starts and manages the main game.
     */
    public void play() {
        dealInitialHands();
        while (!isGameOver()) {
            displayGameState();
            if (humanTurn())    lastPlayer = human;
            if (!isGameOver())
                if (computerTurn()) lastPlayer = computer;
        }
        announceWinner();
    }
    /**
     * Distributes initial dominos to players
     */
    private void dealInitialHands() {
        for (int i = 0; i < 7; i++) {
            human.addDomino(boneyard.draw());
            computer.addDomino(boneyard.draw());
        }
    }
    /**
     * Manages the human player´s turn.
     * @return true if the human played a domino
     */
    private boolean humanTurn() {
        System.out.println("\nYour turn!");
        if (!hasPlayableDomino(human)) {
            System.out.println("No playable domino. Drawing from boneyard.");
            while (!hasPlayableDomino(human) && !boneyard.isEmpty()) {
                Domino drawn = boneyard.draw();
                human.addDomino(drawn);
                System.out.println("Drew: " + drawn);
                if (canPlay(drawn)) break;
            }
            if (!hasPlayableDomino(human)) {
                System.out.println("No playable domino. Skipping turn.");
                return false;
            }
        }
        return playHumanDomino();
    }
    /**
     * Handles the human player's process of choosing and playing a domino.
     * @return true if a domino was placed on the board
     */
    private boolean playHumanDomino() {
        while (true) {
            System.out.println("Choose a domino (1-" +
                    human.getHand().size() + "):");
            int choice = scanner.nextInt() - 1;
            if (choice < 0 || choice >= human.getHand().size()) {
                System.out.println("Invalid choice.");
                continue;
            }
            Domino chosen = human.getHand().get(choice);
            if (!canPlay(chosen)) {
                System.out.println("That domino cannot be played.");
                continue;
            }
            System.out.println("Play on left (L) or right (R)?");
            boolean playLeft = scanner.next().toUpperCase().equals("L");
            System.out.println("Rotate? (Y/N)");
            if (scanner.next().toUpperCase().equals("Y")) chosen.rotate();

            if (isValidPlay(chosen, playLeft)) {
                board.playDomino(chosen, playLeft);
                human.removeDomino(chosen);
                return true;
            }
            System.out.println("Invalid play. Try again.");
            // undo rotate so the domino is restored
            if (!isValidPlay(chosen, playLeft)) chosen.rotate();
        }
    }
    /**
     * Manages the computer player´s turn.
     * @return true if the computer played a domino
     */
    private boolean computerTurn() {
        System.out.println("\nComputer's turn!");
        if (!hasPlayableDomino(computer)) {
            System.out.println("Computer drawing from boneyard.");
            while (!hasPlayableDomino(computer) && !boneyard.isEmpty()) {
                Domino drawn = boneyard.draw();
                computer.addDomino(drawn);
                if (canPlay(drawn)) break;
            }
            if (!hasPlayableDomino(computer)) {
                System.out.println("Computer skips turn.");
                return false;
            }
        }
        for (Domino d : computer.getHand()) {
            if (canPlayLeft(d)) {
                if (!isValidPlay(d, true)) d.rotate();
                board.playDomino(d, true);
                computer.removeDomino(d);
                System.out.println("Computer played: " + d + " on the left");
                return true;
            }
            if (canPlayRight(d)) {
                if (!isValidPlay(d, false)) d.rotate();
                board.playDomino(d, false);
                computer.removeDomino(d);
                System.out.println("Computer played: " + d + " on the right");
                return true;
            }
        }
        return false;
    }
    /**
     * Handles the computer player´s process of choosing and playing a domino
     * @return true if a domino was place in the board
     */
    private boolean playComputerDomino() {
        List<Domino> playableDominos = computer.getHand().stream()
                .filter(this::canPlay)
                .toList();

        Domino chosenDomino = playableDominos.get(0);
        boolean playOnLeft = canPlayLeft(chosenDomino);

        if (!isValidPlay(chosenDomino, playOnLeft)) {
            chosenDomino.rotate();
        }

        board.playDomino(chosenDomino, playOnLeft);
        computer.removeDomino(chosenDomino);
        System.out.println("Computer played: " + chosenDomino +
                (playOnLeft ? " on the left" : " on the right"));
        return true;
    }
    /**
     * Checks if a player has a playable dominos.
     * @param p the player to check
     * @return true if the player has a playable domino
     */
    private boolean hasPlayableDomino(Player p) {
        return p.getHand().stream().anyMatch(this::canPlay);
    }
    /**
     * Checks if a domino can be played
     * @param d the domino to check
     * @return true if the domino can be played
     */
    private boolean canPlay(Domino d) {
        return canPlayLeft(d) || canPlayRight(d);
    }
    /**
     * Checks if a domino can be played on the left end of the board.
     * @param d the domino to check
     * @return true if the domino can be played on the left
     */
    private boolean canPlayLeft(Domino d) {
        if (board.isEmpty()) return true;
        int end = board.getLeftEnd();
        return d.getLeft() == end || d.getRight() == end;
    }
    /**
     * Checks if a domino can be played on the right end of the board.
     * @param d the domino to check
     * @return true if the domino can be played on the right
     */
    private boolean canPlayRight(Domino d) {
        if (board.isEmpty()) return true;
        int end = board.getRightEnd();
        return d.getLeft() == end || d.getRight() == end;
    }
    /**
     * Checks if playing a domino is valid
     * @param d the domino to play
     * @param playLeft true if playing on the left end
     * @return true if the play is valid
     */
    private boolean isValidPlay(Domino d, boolean playLeft) {
        if (board.isEmpty()) return true;
        if (playLeft)  return d.getRight() == board.getLeftEnd();
        else           return d.getLeft()  == board.getRightEnd();
    }
    /**
     * Checks if the game is over
     * @return true if the game is over
     */
    private boolean isGameOver() {
        return human.getHand().isEmpty() || computer.getHand().isEmpty() ||
                (boneyard.isEmpty() && !hasPlayableDomino(human) &&
                        !hasPlayableDomino(computer));
    }
    /**
     * Displays the current state of the game.
     */
    private void displayGameState() {
        System.out.println("\nBoard: " + board);
        System.out.println("Your hand: " + human.getHand());
        System.out.println("Computer: " + computer.getHand().size() +
                " dominos | Boneyard: " + boneyard.size());
    }
    /**
     * Announces the winner of the game.
     */
    private void announceWinner() {
        System.out.println("\nGame Over!");
        displayGameState();
        int h = human.getScore();
        int c = computer.getScore();
        System.out.println("Your score: " + h +
                " | Computer score: " + c);
        if (h < c)       System.out.println("You win!");
        else if (c < h)  System.out.println("Computer wins!");
        else {
            System.out.println("Tie! " +
                    (lastPlayer == human ? "You" : "Computer") +
                    " played last and wins.");
        }
    }
    /**
     * Main method to start the game
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new DominoGame().play();
    }
}
