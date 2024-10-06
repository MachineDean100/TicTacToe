import javax.swing.*;

public class TicTacToeRunner {
    /**
     * Main method to run the Tic-Tac-Toe game.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeFrame::new);
    }
}
