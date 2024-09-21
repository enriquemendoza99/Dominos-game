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
        human = new Player("Human");
        computer = new Player("Computer");
        boneyard = new Boneyard();
        board = new GameBoard();
        lastPlayer = null;
        gameOver = false;
        dealInitialHands();
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
        if (gameOver) return false;
        if (!boneyard.isEmpty() && !hasPlayableDomino(human)) {
            human.addDomino(boneyard.draw());
            return true;
        }
        return false;
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
        if (canPlay(domino, playLeft)) {
            Domino playedDomino = new Domino(domino.getLeft(),
                    domino.getRight());
            if (rotate) {
                playedDomino.rotate();
            }
            if (!isValidPlay(playedDomino, playLeft)) {
                return false;
            }
            board.playDomino(playedDomino, playLeft);
            human.removeDomino(domino);
            lastPlayer = human;
            checkGameOver();
            if (!gameOver) {
                computerTurn();
            }
            return true;
        }
        return false;
    }

    /**
     * Manages the computer player´s turn.
     */
    private void computerTurn() {
        while (!gameOver) {
            List<Domino> playableDominos = computer.getHand().stream()
                    .filter(d -> canPlay(d, true) ||
                            canPlay(d, false))
                    .toList();

            if (playableDominos.isEmpty()) {
                if (boneyard.isEmpty()) {
                    break;
                }
                computer.addDomino(boneyard.draw());
            } else {
                Domino chosenDomino = playableDominos.get(0);
                boolean playLeft = canPlay(chosenDomino, true);
                Domino playedDomino = new Domino(chosenDomino.getLeft(),
                        chosenDomino.getRight());
                if (!isValidPlay(playedDomino, playLeft)) {
                    playedDomino.rotate();
                }
                board.playDomino(playedDomino, playLeft);
                computer.removeDomino(chosenDomino);
                lastPlayer = computer;
                checkGameOver();
                break;
            }
        }
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
     * Checks if playing a domino is valid
     * @param domino The domino to be played
     * @param playLeft true if playing on the left end
     * @return true if the play is valid
     */
    private boolean isValidPlay(Domino domino, boolean playLeft) {
        if (board.isEmpty()) return true;
        if (playLeft) {
            int leftEnd = board.getLeftEnd();
            return domino.getRight() == leftEnd || domino.getRight() == 0 ||
                    leftEnd == 0;
        } else {
            int rightEnd = board.getRightEnd();
            return domino.getLeft() == rightEnd || domino.getLeft() == 0 ||
                    rightEnd == 0;
        }
    }

    /**
     * Checks if the game is over.
     */
    private void checkGameOver() {
        gameOver = human.getHand().isEmpty() || computer.getHand().isEmpty() ||
                (boneyard.isEmpty() && !hasPlayableDomino(human) &&
                        !hasPlayableDomino(computer));

        if (gameOver && (human.getHand().isEmpty() ||
                computer.getHand().isEmpty())) {
            lastPlayer = human.getHand().isEmpty() ? human : computer;
        }
    }

    /**
     * Checks if a player has any playable dominos.
     * @param player The player to check
     * @return true if the player has a playable domino
     */
    private boolean hasPlayableDomino(Player player) {
        // the function anyMatch return true if any domino in the player´s hand
        // can be played.
        // d->canPlay(d,true)||canPlay(d,false) It´s the predicate for anyMatch
        // Checks if the domino can be played on the left end or right end
        return player.getHand().stream().anyMatch(d -> canPlay(d, true)
                || canPlay(d, false));
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
        if (gameOver) {
            int humanScore = calculateFinalScore(human);
            int computerScore = calculateFinalScore(computer);

            if (humanScore < computerScore) {
                return "Game Over! You win with a score of " +
                        humanScore + " vs " + computerScore + "!";
            } else if (computerScore < humanScore) {
                return "Game Over! Computer wins with a score of " +
                        computerScore + " vs " + humanScore + "!";
            } else {
                // If scores are tied, the last player to play a domino wins
                return lastPlayer == human ?
                        "Game Over! It's a tie (" + humanScore + " points " +
                                "each), but you played last, so you win!" :
                        "Game Over! It's a tie (" + computerScore +
                                " points each), but the computer played last," +
                                " so the computer wins!";
            }
        } else {
            return "Your turn! Select a domino to play or draw " +
                    "from the boneyard.";
        }
    }

    /**
     * Resets the game to initialize the game.
     */
    public void resetGame() {
        initializeGame();
    }
}
