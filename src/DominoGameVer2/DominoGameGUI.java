package DominoGameVer2;

/**
 * Represents the graphical user interface for the Domino game.
 * @author Enrique Mendoza
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DominoGameGUI extends JFrame {
    //The main game logic object.
    private DominoGame game;
    // Panel for displaying the game board.
    private JPanel boardPanel;
    // Panel for displaying the player´s hand.
    private JPanel playerHandPanel;
    // Button for drawing a domino from the boneyard.
    private JButton drawButton;
    // Label for displaying game messages.
    private JLabel messageLabel;
    //Constants for domino and board dimensions
    private static final int DOMINO_WIDTH = 70;
    private static final int DOMINO_HEIGHT = 35;
    private static final int DOMINO_SPACING = 5;

    /**
     * Initializes the game and UI
     */
    public DominoGameGUI() {
        game = new DominoGame();
        initializeUI();
    }

    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        setTitle("Domino Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(800, 200));
        boardPanel.setBackground(Color.GREEN.darker());
        add(boardPanel, BorderLayout.CENTER);

        playerHandPanel = new JPanel();
        playerHandPanel.setBackground(Color.LIGHT_GRAY);
        add(playerHandPanel, BorderLayout.SOUTH);

        drawButton = new JButton("Draw");
        drawButton.addActionListener(e -> {
            if (game.drawForHuman()) {
                updateUI();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Cannot draw — you have a playable domino " +
                                "or the boneyard is empty.");
            }
        });
        add(drawButton, BorderLayout.EAST);

        messageLabel = new JLabel("Welcome to Domino Game!");
        add(messageLabel, BorderLayout.NORTH);

        setSize(800, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        updateUI();
    }

    /**
     * Draws the game board with the current state of played dominos.
     * @param g Graphic object to draw on
     */
    private void drawBoard(Graphics g) {
        List<Domino> dominos = game.getBoardDominos();
        if (dominos.isEmpty()) return;

        int centerY    = boardPanel.getHeight() / 2;
        int centerX    = boardPanel.getWidth()  / 2;
        int totalWidth = (dominos.size() * (DOMINO_WIDTH / 2)) +
                ((dominos.size() - 1) * DOMINO_SPACING);
        int startX     = centerX - (totalWidth / 2);

        for (int i = 0; i < dominos.size(); i++) {
            int x = startX + (i * (DOMINO_WIDTH / 2)) + (i * DOMINO_SPACING);
            int y = (i % 2 == 0) ? centerY - DOMINO_HEIGHT - 5 : centerY + 5;
            drawDomino(g, dominos.get(i), x, y);
        }
    }
    /**
     * Draws a single domino on the board.
     * @param g Graphic object to draw on
     * @param domino Domino to be drawn
     * @param x X-coordinate for drawing
     * @param y Y-coordinate for drawing
     */
    private void drawDomino(Graphics g, Domino domino, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x, y, DOMINO_WIDTH - 1, DOMINO_HEIGHT - 1, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x, y, DOMINO_WIDTH - 1, DOMINO_HEIGHT - 1, 10, 10);
        g2d.drawLine(x + DOMINO_WIDTH / 2, y,
                x + DOMINO_WIDTH / 2, y + DOMINO_HEIGHT);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(String.valueOf(domino.getLeft()), x + 13, y + 24);
        g2d.drawString(String.valueOf(domino.getRight()),
                x + DOMINO_WIDTH / 2 + 13, y + 24);
    }

    /**
     * Updates all UI components to reflect the current game state
     */
    public void updateUI() {
        boardPanel.repaint();
        updatePlayerHand();
        messageLabel.setText(game.getGameStateMessage());
        drawButton.setEnabled(game.canHumanDraw());

        if (game.isGameOver()) {
            SwingUtilities.invokeLater(() -> {
                int opt = JOptionPane.showConfirmDialog(this,
                        game.getGameStateMessage() +
                                "\nWould you like to play again?",
                        "Game Over", JOptionPane.YES_NO_OPTION);
                if (opt == JOptionPane.YES_OPTION) {
                    game.resetGame();
                    updateUI();
                } else {
                    System.exit(0);
                }
            });
        }
    }

    /**
     * Updates the display of the player's hand
     */
    private void updatePlayerHand() {
        playerHandPanel.removeAll();
        playerHandPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        for (Domino domino : game.getHumanHand()) {
            JButton btn = new JButton(domino.getLeft() + "|" + domino.getRight());
            btn.setPreferredSize(new Dimension(70, 35));
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.addActionListener(e -> playDomino(domino));
            playerHandPanel.add(btn);
        }
        playerHandPanel.revalidate();
        playerHandPanel.repaint();
    }

    /**
     * Handles the logic when a player attempts to play a domino.
     * @param domino Domino selected to play
     */
    private void playDomino(Domino domino) {
        String[] sides = {"Left", "Right"};
        int side = JOptionPane.showOptionDialog(this,
                "Where do you want to play?", "Choose Side",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, sides, sides[0]);
        int rot = JOptionPane.showConfirmDialog(this,
                "Rotate the domino?", "Rotate", JOptionPane.YES_NO_OPTION);

        boolean playLeft     = (side == 0);
        boolean shouldRotate = (rot == JOptionPane.YES_OPTION);

        if (!game.playHumanDomino(domino, playLeft, shouldRotate)) {
            JOptionPane.showMessageDialog(this, "Invalid move. Try again.");
        } else {
            updateUI();
        }
    }

    /**
     * Updates the message displayed to the user.
     * @param message the message to display
     */
    private void updateMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DominoGameGUI::new);
    }
}
