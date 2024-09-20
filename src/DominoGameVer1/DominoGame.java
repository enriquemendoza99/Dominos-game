package DominoGameVer1;

/**
 * Represents the main game logic for the Domino game
 * @author Enrique Mendoza
 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DominoGame {
    private Player human;
    private Player computer;
    private Boneyard boneyard;
    private GameBoard board;
    private Scanner scanner;
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
            if (humanTurn()) {
                lastPlayer = human;
            }
            if (!isGameOver()) {
                if (computerTurn()) {
                    lastPlayer = computer;
                }
            }
        }
        winnerAnnounce();
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
            System.out.println("You don't have a playable domino. " +
                    "Drawing from boneyard.");
            while (!hasPlayableDomino(human) && !boneyard.isEmpty()) {
                Domino drawn = boneyard.draw();
                human.addDomino(drawn);
                System.out.println("Drew: " + drawn);
                if (canPlay(drawn)) {
                    System.out.println("You can play this domino!");
                    break;
                }
            }
            if (!hasPlayableDomino(human)) {
                System.out.println("Still no playable domino. Skipping turn.");
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
        Domino chosenDomino;
        boolean playOnLeft;
        boolean rotate;
        while (true) {
            System.out.println("Choose a domino to play (1-" +
                    human.getHand().size() + "):");
            int choice = scanner.nextInt() - 1;
            if (choice >= 0 && choice < human.getHand().size()) {
                chosenDomino = human.getHand().get(choice);
                if (canPlay(chosenDomino)) {
                    System.out.println("Play on left (L) or right (R)?");
                    String side = scanner.next().toUpperCase();
                    playOnLeft = side.equals("L");

                    System.out.println("Rotate the domino? (Y/N)");
                    rotate = scanner.next().toUpperCase().equals("Y");

                    if (rotate) {
                        chosenDomino.rotate();
                    }

                    if (isValidPlay(chosenDomino, playOnLeft)) {
                        board.playDomino(chosenDomino, playOnLeft);
                        human.removeDomino(chosenDomino);
                        return true;
                    } else {
                        System.out.println("Invalid play. Try another domino.");
                        if (rotate) {
                            chosenDomino.rotate();
                        }
                    }
                } else {
                    System.out.println("You can't play that domino. " +
                            "Choose another.");
                }
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
    /**
     * Manages the computer player´s turn.
     * @return true if the computer played a domino
     */
    private boolean computerTurn() {
        System.out.println("\nComputer's turn!");
        if (!hasPlayableDomino(computer)) {
            System.out.println("Computer doesn't have a playable domino. " +
                    "Drawing from boneyard.");
            while (!hasPlayableDomino(computer) && !boneyard.isEmpty()) {
                Domino drawn = boneyard.draw();
                computer.addDomino(drawn);
                System.out.println("Computer drew a domino.");
                if (canPlay(drawn)) {
                    System.out.println("Computer can play the drawn domino.");
                    break;
                }
            }
            if (!hasPlayableDomino(computer)) {
                System.out.println("Computer doesn't have a playable domino." +
                        "Skipping turn.");
                return false;
            }
        }
        return playComputerDomino();
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
     * @param player the player to check
     * @return true if the player has a playable domino
     */
    private boolean hasPlayableDomino(Player player) {
        return player.getHand().stream().anyMatch(this::canPlay);
    }
    /**
     * Checks if a domino can be played
     * @param domino the domino to check
     * @return true if the domino can be played
     */
    private boolean canPlay(Domino domino) {
        return canPlayLeft(domino) || canPlayRight(domino);
    }
    /**
     * Checks if a domino can be played on the left end of the board.
     * @param domino the domino to check
     * @return true if the domino can be played on the left
     */
    private boolean canPlayLeft(Domino domino) {
        int leftEnd = board.getLeftEnd();
        return domino.getLeft() == leftEnd || domino.getRight() == leftEnd ||
                domino.getLeft() == 0 || domino.getRight() == 0||leftEnd == 0;
    }
    /**
     * Checks if a domino can be played on the right end of the board.
     * @param domino the domino to check
     * @return true if the domino can be played on the right
     */
    private boolean canPlayRight(Domino domino) {
        int rightEnd = board.getRightEnd();
        return domino.getLeft() == rightEnd || domino.getRight() == rightEnd ||
                domino.getLeft() == 0 || domino.getRight() == 0 ||
                rightEnd == 0;
    }
    /**
     * Checks if playing a domino is valid
     * @param domino the domino to play
     * @param playOnLeft true if playing on the left en
     * @return true if the play is valid
     */
    private boolean isValidPlay(Domino domino, boolean playOnLeft) {
        if (playOnLeft) {
            return domino.getRight() == board.getLeftEnd() ||
                    domino.getRight() == 0 || board.getLeftEnd() == 0;
        } else {
            return domino.getLeft() == board.getRightEnd() ||
                    domino.getLeft() == 0 || board.getRightEnd() == 0;
        }
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
        System.out.println("\nGame Board: " + board);
        System.out.println("Your hand: " + human.getHand());
        System.out.println("Computer has " + computer.getHand().size()
                + " dominos");
        System.out.println("Boneyard has " + boneyard.size() + " dominos");
    }
    /**
     * Announces the winner of the game.
     */
    private void winnerAnnounce() {
        System.out.println("\nGame Over!");
        displayGameState();
        int humanScore = human.getScore();
        int computerScore = computer.getScore();
        System.out.println("Your score: " + humanScore);
        System.out.println("Computer's score: " + computerScore);

        if (humanScore < computerScore) {
            System.out.println("You win!");
        } else if (computerScore < humanScore) {
            System.out.println("Computer wins!");
        } else {
            if (lastPlayer == human) {
                System.out.println("It's a tie, but you played last, " +
                        "so you win!");
            } else {
                System.out.println("It's a tie, but the computer played last,"+
                        "so the computer wins!");
            }
        }
    }
    /**
     * Main method to start the game
     * @param args command line arguments
     */
    public static void main(String[] args) {
        DominoGame game = new DominoGame();
        game.play();
    }
}
