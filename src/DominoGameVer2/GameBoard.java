package DominoGameVer2;

/**
 * Represents the game board where dominos are played.
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    //List of dominos played on the board.
    private List<Domino> dominos;
    //List of positions for each played domino.
    private List<Point> dominoPositions;
    // The values at the left and right ends of the board
    private int leftEnd;
    private int rightEnd;

    /**
     * Initializes an empty game board.
     */
    public GameBoard() {
        dominos = new ArrayList<>();
        dominoPositions = new ArrayList<>();
    }

    /**
     * Plays a domino on the board
     * @param domino the domino to play
     * @param playLeft true if playing on the left end
     */
    public void playDomino(Domino domino, boolean playLeft) {
        if (isEmpty()) {
            dominos.add(domino);
            // Initial position
            dominoPositions.add(new Point(0, 0));
            leftEnd = domino.getLeft();
            rightEnd = domino.getRight();
        } else if (playLeft) {
            dominos.add(0, domino);
            // Leftmost position
            dominoPositions.add(0,
                    new Point(dominoPositions.get(0).x - 1, 0));
            leftEnd = domino.getLeft();
        } else {
            dominos.add(domino);
            // Rightmost position
            dominoPositions.add(new Point(
                    dominoPositions.get(dominoPositions.size() - 1).x + 1,
                    0));
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
     * Gets a copy of the list of dominos on the board.
     * @return list of dominos on the board
     */
    public List<Domino> getDominos() { return new ArrayList<>(dominos); }

    /**
     * Checks if the board is empty
     * @return true if the board is empty
     */
    public boolean isEmpty() { return dominos.isEmpty(); }
}
