/**
 * Represents a single domino in the game
 */
public class Domino {
    // The left and right values of the domino
    private int left;
    private int right;

    /**
     * Constructor to create a new Domino.
     * @param left the left value of the domino
     * @param right the right value of the domino
     */
    public Domino(int left, int right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Gets the left value of the domino
     * @return the left value
     */
    public int getLeft() { return left; }

    /**
     * Gets the right value of the domino
     * @return the right value
     */
    public int getRight() { return right; }

    /**
     * Rotates the domino by swapping its left and right values
     */
    public void rotate() {
        int temp = left;
        left = right;
        right = temp;
    }

    /**
     * Calculates the sum of the domino´s values
     * @return the sum of left and right values
     */
    public int getSum() { return left + right; }

    /**
     * Provides a string representation of the domino
     * @return a string in the format [left/right]
     */
    @Override
    public String toString() {
        return "[" + left + "|" + right + "]";
    }
}