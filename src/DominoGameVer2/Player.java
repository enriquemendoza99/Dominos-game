package DominoGameVer2;

/**
 * Represents a player in the Domino Game.
 */
import java.util.ArrayList;
import java.util.List;
public class Player {
    // The player's hand of dominos
    private List<Domino> hand;
    // String representing the player´s name.
    private String name;

    /**
     * Initializes a player with a given name.
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    /**
     * Adds a domino to the player's hand.
     * @param domino the domino to add
     */
    public void addDomino(Domino domino) {
        hand.add(domino);
    }

    /**
     * Gets the player's current hand
     * @return list of dominos in the player's hand
     */
    public List<Domino> getHand() { return new ArrayList<>(hand); }

    /**
     * Removes a domino from the player´s hand.
     * @param domino the domino to remove
     */
    public void removeDomino(Domino domino) {
        hand.remove(domino);
    }
}
