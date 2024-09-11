/**
 * Represents a single domino piece in the game. Each one has a left and right
 * value.
 */
public class Domino {
    private int leftValue;
    private int rightValue;

    /**
     * Constructs a new Domino with the left and right values
     * @param leftValue the value on the left side of the domino.
     * @param rightValue the value on the right side of the domino.
     */
    public Domino(int leftValue, int rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    /**
     * Rotates the domino by swapping its left and right values.
     */
    public void rotate() {
        int temp = leftValue;
        leftValue = rightValue;
        rightValue = temp;
    }

    /**
     * Check if the domino can be connected to another domino
     * @param other the other domino to check for a match
     * @return true if the domino can connect to the other domino
     */
    public boolean matches(Domino other) {
        return this.leftValue == other.rightValue || this.rightValue == other.leftValue;
    }

    /**
     * Returns a string representation of the domino
     * @return a string in the format "[leftValue, rightValue]"
     */
    @Override
    public String toString() {
        return "[" + leftValue + " " + rightValue + "]";
    }

    /**
     * Gets the left value of the domino
     * @return the left value
     */
    public int getLeftValue() { return leftValue; }

    /**
     * Gets the right value of the domino
     * @return the right value
     */
    public int getRightValue() { return rightValue; }
}

