import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player's hand in the game.
 * A hand is a collection of dominos that a player holds
 */
public class Hand {
    private List<Domino> dominos;

    /**
     * Construct a new and empty hand
     */
    public Hand() {
        this.dominos = new ArrayList<>();
    }

    /**
     * Adds a domino to the hand
     * @param domino the domino to be added
     */
    public void addDomino(Domino domino) {
        dominos.add(domino);
    }

    /**
     * Removes a domino from the hand
     * @param domino the domino to be removed
     */
    public void removeDomino(Domino domino) {
        dominos.remove(domino);
    }

    /**
     * Check if the hand contains a specific domino
     * @param domino the domino to check for
     * @return true if the hand contains the domino
     */
    public boolean hasDomino(Domino domino) {
        return dominos.contains(domino);
    }

    /**
     * Get a list of all dominos in the hand
     * @return A new ArrayList containing all dominos in the hand
     */
    public List<Domino> getDominos() {
        return new ArrayList<>(dominos);
    }

    /**
     * Returns a string representation of the hand
     * @return a string representation of all dominos in the hand.
     */
    @Override
    public String toString() {
        return dominos.toString();
    }
}
