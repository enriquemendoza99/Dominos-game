package DominoGameVer2;

/**
 * Represents the boneyard in the Domino game
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boneyard {
    // List of dominos in the boneyard
    private List<Domino> dominos;

    /**
     * Initializes the boneyard with a full set of dominos and shuffles them.
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
     * Draws a domino from the boneyard
     * @return the draw domino, or null if boneyard is empty
     */
    public Domino draw() {
        if (dominos.isEmpty()) return null;
        return dominos.remove(dominos.size() - 1);
    }

    /**
     * Checks if the boneyard is empty
     * @return true if the boneyard is empty
     */
    public boolean isEmpty() {
        return dominos.isEmpty();
    }
}
