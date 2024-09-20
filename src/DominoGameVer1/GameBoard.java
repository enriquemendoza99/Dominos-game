package DominoGameVer1;

/**
 * Represents the game board where dominos are played
 */

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    // The list of dominos played on the board
    private List<Domino> playedDominos;
    // The values at the left and right ends of the board
    private int leftEnd;
    private int rightEnd;

    /**
     * Constructor for a new GameBoard
     */
    public GameBoard() {
        playedDominos = new ArrayList<>();
    }

    /**
     * Plays a domino on the board
     * @param domino the domino to play
     * @param playOnLeft true if playing on the left end
     */
    public void playDomino(Domino domino, boolean playOnLeft) {
        if (playedDominos.isEmpty()) {
            playedDominos.add(domino);
            leftEnd = domino.getLeft();
            rightEnd = domino.getRight();
        } else if (playOnLeft) {
            if (domino.getRight() != leftEnd && domino.getLeft() !=
                    leftEnd && leftEnd != 0 && domino.getRight() != 0
                    && domino.getLeft() != 0) {
                domino.rotate();
            }
            playedDominos.add(0, domino);
            leftEnd = domino.getLeft();
        } else {
            if (domino.getLeft() != rightEnd && domino.getRight() !=
                    rightEnd && rightEnd != 0 && domino.getLeft() != 0
                    && domino.getRight() != 0) {
                domino.rotate();
            }
            playedDominos.add(domino);
            rightEnd = domino.getRight();
        }
    }

    /**
     * Gets the value at the left end of the board.
     * @return the left end value
     */
    public int getLeftEnd() { return leftEnd; }

    /**
     * Gets the value at the right end of the board.
     * @return the right end value
     */
    public int getRightEnd() { return rightEnd; }

    /**
     * Provides a string representation of the game board
     * @return a string showing all played dominos
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Domino domino : playedDominos) {
            string.append(domino).append(" ");
        }
        return string.toString();
    }
}
