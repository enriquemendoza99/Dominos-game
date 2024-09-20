/**
 * Represents the boneyard in the Domino game
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boneyard {
    // The list of dominos in the boneyard
    private List<Domino> dominos;

    /**
     * Constructor to create a new Boneyard
     */
    public Boneyard() {
        dominos = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                dominos.add(new Domino(i, j));
            }
        }
        Collections.shuffle(dominos);
    }

    /**
     * Draws a domino from the boneyard.
     * @return the draw domino or null if boneyard is empty
     */
    public Domino draw() {
        if (dominos.isEmpty()) return null;
        return dominos.remove(dominos.size() - 1);
    }

    /**
     * Check if the boneyard is empty
     * @return true if the boneyard is empty
     */
    public boolean isEmpty() {
        return dominos.isEmpty();
    }

    /**
     * Gets the current size of the boneyard
     * @return the number of dominos left in the boneyard
     */
    public int size() {
        return dominos.size();
    }
}