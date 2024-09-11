/**
 * Represents a player in the game. Each player has a name, a hand of
 * dominos, and a score.
 */
public class Player {
    private String name;
    private Hand hand;
    private int score;

    /**
     * Construct a new Player with the given name.
     * Initializes an empty hand and the score is 0 when starts.
     * @param name the name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.score = 0;
    }

    /**
     * Removes a domino from the player´s hand
     * @param domino the domino to be played on the board.
     */
    public void playDomino(Domino domino) {
        hand.removeDomino(domino);
    }

    /**
     * Adds a domino to the player´s hand.
     * @param domino the domino to be added to the hand.
     */
    public void drawDomino(Domino domino) {
        hand.addDomino(domino);
    }

    /**
     * Calculate the score based on the dominos in their hand.
     */
    public void calculateScore() {
        score = hand.getDominos().stream()
                .mapToInt(d -> d.getLeftValue() + d.getRightValue())
                .sum();
    }

    /**
     * Gets the name of the player
     * @return the player´s name
     */
    public String getName() { return name; }

    /**
     * Gets the player´s hand.
     * @return the player´s hand object
     */
    public Hand getHand() { return hand; }

    /**
     * Gets the player´s current score
     * @return the player´s score.
     */
    public int getScore() { return score; }
}
