import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private Game game;
    private JButton[][] buttons;
    private JLabel statusLabel;

    public GameFrame() {
        game = new Game();
        buttons = new JButton[3][3];
        statusLabel = new JLabel("Player X's turn");

        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));

        // Initialize buttons for each cell
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                final int r = row;
                final int c = col;
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleMove(r, c);
                    }
                });
                boardPanel.add(buttons[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        setSize(400, 400);
        setVisible(true);
    }

    private void handleMove(int row, int col) {
        if (game.isGameOver()) return;

        // Get the current player before making the move, to use in button text
        char player = game.getCurrentPlayer();

        // Make the move and check if it's valid
        if (game.makeMove(row, col)) {
            // Set the button text to the current player before switching turn
            buttons[row][col].setText(String.valueOf(player));
            updateStatus();

            // Check if the game has ended and show the result
            if (game.isGameOver()) {
                showGameResult();
            }
        }
    }

    private void updateStatus() {
        if (!game.isGameOver()) {
            // Show the next player's turn if the game is not over
            statusLabel.setText("Player " + game.getCurrentPlayer() + "'s turn");
        }
    }

    private void showGameResult() {
        // Determine the result message
        String message;
        if (game.getBoard().checkWinner(game.getCurrentPlayer())) {
            message = "Player " + game.getCurrentPlayer() + " wins!";
        } else {
            message = "It's a draw!";
        }

        // Show a dialog box with the result and options to play again or quit
        int response = JOptionPane.showOptionDialog(
                this,
                message + "\nWould you like to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Yes", "Quit"},
                "Yes"
        );

        // Handle the user's choice
        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0); // Quit the game
        }
    }

    private void resetGame() {
        game.resetGame();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(" ");
            }
        }
        statusLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
