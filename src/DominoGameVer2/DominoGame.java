package DominoGameVer2;

/**
 * Represents the core game logic for the Domino game.
 */
import java.awt.Point;
import java.util.List;
public class DominoGame {
    // Represent the human player
    private Player human;
    //Represents the computer player
    private Player computer;
    //Represents the pool of undrawn dominos
    private Boneyard boneyard;
    //Represent the current state of the game board
    private GameBoard board;
    //Player who made the last move
    private Player lastPlayer;
    //Indicates if the game has ended
    private boolean gameOver;

    /**
     * Constructor to initializes a new game
     */
    public DominoGame() {
        initializeGame();
    }

    /**
     * Initializes a new game.
     */
    private void initializeGame() {
        human      = new Player("Human");
        computer   = new Player("Computer");
        boneyard   = new Boneyard();
        board      = new GameBoard();
        lastPlayer = null;
        gameOver   = false;
        for (int i = 0; i < 7; i++) {
            human.addDomino(boneyard.draw());
            computer.addDomino(boneyard.draw());
        }
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
     * Allows the player to take a domino from the boneyard.
     * @return true if a domino was drawn
     */
    public boolean drawForHuman() {
        if (gameOver || boneyard.isEmpty() || hasPlayableDomino(human))
            return false;
        human.addDomino(boneyard.draw());
        return true;
    }

    /**
     * Handles the human player's process of choosing and playing a domino.
     * @param domino The domino to be played
     * @param playLeft Played on the left side of the board
     * @param rotate Rotate the domino before playing
     * @return true if the move is valid
     */
    public boolean playHumanDomino(Domino domino, boolean playLeft,
                                   boolean rotate) {
        if (gameOver) return false;

        Domino played = new Domino(domino.getLeft(), domino.getRight());
        if (rotate) played.rotate();

        if (!isValidPlay(played, playLeft)) return false;

        board.playDomino(played, playLeft);
        human.removeDomino(domino);
        lastPlayer = human;
        checkGameOver();
        if (!gameOver) computerTurn();
        return true;
    }

    /**
     * Manages the computer player´s turn.
     */
    private void computerTurn() {
        while (!gameOver) {
            Domino chosen   = null;
            boolean playLeft = true;

            for (Domino d : computer.getHand()) {
                if (canPlayLeft(d))  { chosen = d; playLeft = true;  break; }
                if (canPlayRight(d)) { chosen = d; playLeft = false; break; }
            }

            if (chosen == null) {
                if (boneyard.isEmpty()) break;
                computer.addDomino(boneyard.draw());
                continue;
            }

            Domino played = new Domino(chosen.getLeft(), chosen.getRight());
            if (!isValidPlay(played, playLeft)) played.rotate();

            board.playDomino(played, playLeft);
            computer.removeDomino(chosen);
            lastPlayer = computer;
            checkGameOver();
            break;
        }
    }

    /**
     * Checks if a domino can play on the left end.
     * One of its values must match the current left end of the board.
     */
    private boolean canPlayLeft(Domino domino) {
        if (board.isEmpty()) return true;
        int end = board.getLeftEnd();
        return domino.getLeft() == end || domino.getRight() == end;
    }
    /**
     * Checks if a domino can play on the right end.
     * One of its values must match the current right end of the board.
     */
    private boolean canPlayRight(Domino domino) {
        if (board.isEmpty()) return true;
        int end = board.getRightEnd();
        return domino.getLeft() == end || domino.getRight() == end;
    }

    /**
     * Checks if a domino can be played
     * @param domino The domino to check
     * @param playLeft Played on the left side
     * @return true if the domino can be played
     */
    private boolean canPlay(Domino domino, boolean playLeft) {
        if (board.isEmpty()) return true;
        if (playLeft) {
            int leftEnd = board.getLeftEnd();
            return domino.getLeft() == leftEnd || domino.getRight() ==
                    leftEnd || domino.getLeft() == 0 || domino.getRight() == 0
                    || leftEnd == 0;
        } else {
            int rightEnd = board.getRightEnd();
            return domino.getLeft() == rightEnd || domino.getRight() ==
                    rightEnd || domino.getLeft() == 0 || domino.getRight() == 0
                    || rightEnd == 0;
        }
    }

    /**
     * Checks if a domino placement is valid.
     * The connecting face must exactly match the board end.
     * @param domino the domino to check (already rotated if needed)
     * @param playLeft true if placing on the left end
     * @return true if the placement is valid
     */
    private boolean isValidPlay(Domino domino, boolean playLeft) {
        if (board.isEmpty()) return true;
        if (playLeft) return domino.getRight() == board.getLeftEnd();
        else          return domino.getLeft()  == board.getRightEnd();
    }

    /**
     * Checks if the game is over.
     */
    private void checkGameOver() {
        gameOver = human.getHand().isEmpty() ||
                computer.getHand().isEmpty() ||
                (boneyard.isEmpty() && !hasPlayableDomino(human) &&
                        !hasPlayableDomino(computer));
    }

    /**
     * Checks if a player has any playable dominos.
     * @param player The player to check
     * @return true if the player has a playable domino
     */
    private boolean hasPlayableDomino(Player player) {
        return player.getHand().stream()
                .anyMatch(d -> canPlayLeft(d) || canPlayRight(d));
    }

    /**
     * Calculates the final score for a player.
     * @param player The player whose score to calculate
     * @return The sum of the dots on the player's remaining dominos
     */
    private int calculateFinalScore(Player player) {
        // The stream function convert the list of dominos into a stream
        // mapToInt transform the stream of Domino objects into a stream
        // of integers
        return player.getHand().stream().mapToInt(Domino::getSum).sum();
    }

    /**
     * Gets the list of dominos currently on the board.
     * @return List of dominos on the board
     */
    public List<Domino> getBoardDominos() {
        return board.getDominos();
    }

    /**
     * Gets the human player's hand.
     * @return List of dominos in the human player's hand
     */
    public List<Domino> getHumanHand() {
        return human.getHand();
    }

    /**
     * Checks if the human player can draw a domino.
     * @return true if the human can draw
     */
    public boolean canHumanDraw() {
        return !gameOver && !boneyard.isEmpty() && !hasPlayableDomino(human);
    }

    /**
     * Checks if the game is over.
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the current game state message.
     * @return A string describing the current game state
     */
    public String getGameStateMessage() {
        if (!gameOver)
            return "Your turn! Select a domino to play or draw from boneyard.";
        int h = human.getHand().stream().mapToInt(Domino::getSum).sum();
        int c = computer.getHand().stream().mapToInt(Domino::getSum).sum();
        if (h < c) return "Game Over! You win! (" + h + " vs " + c + ")";
        if (c < h) return "Game Over! Computer wins! (" + c + " vs " + h + ")";
        return "Game Over! Tie (" + h + " each). " +
                (lastPlayer == human ? "You" : "Computer") +
                " played last and wins!";
    }

    /**
     * Resets the game to initialize the game.
     */
    public void resetGame() {
        initializeGame();
    }
}
