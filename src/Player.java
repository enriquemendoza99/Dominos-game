/**
 * Represents a player in the Domino Game
 */

import java.util.ArrayList;
import java.util.List;

public class Player {
    // The player's hand of dominos
    private List<Domino> hand;
    // The player's name
    private String name;

    /**
     * Construtor to create a new Player
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    /**
     * Adds a domino to the player's hand
     * @param domino the domino to add
     */
    public void addDomino(Domino domino) {
        hand.add(domino);
    }

    /**
     * Gets the player's current hand
     * @return the list of dominos in the player's hand
     */
    public List<Domino> getHand() { return hand; }

    /**
     * Calculates the player's score based on the sum of dominos in their hand
     * @return the total score
     */
    public int getScore() {
        //maptoInt is a method of the Stream interface that allows you to
        // transform a stream of objects into a stream of primitive int values.
        return hand.stream().mapToInt(Domino::getSum).sum();
    }

    /**
     * Removes a domino from the player's hand
     * @param domino the domino to remove
     */
    public void removeDomino(Domino domino) {
        hand.remove(domino);
    }
}