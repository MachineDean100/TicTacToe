import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeFrame extends JFrame implements ActionListener {

    private JButton[][] buttons;
    private char[][] board;
    private boolean playerXTurn;

    private JLabel statusLabel;

    public TicTacToeFrame() {
        super("Tic-Tac-Toe");
        buttons = new JButton[3][3];
        board = new char[3][3];
        playerXTurn = true;

        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));

        Font buttonFont = new Font("Arial", Font.BOLD, 60);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttonFont);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
                board[i][j] = ' ';
            }
        }

        statusLabel = new JLabel("Player X's turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));

        mainPanel.add(statusLabel, BorderLayout.NORTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        newGameItem.addActionListener(e -> resetGame());
        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(newGameItem);
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        int row = -1;
        int col = -1;

        outerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (clickedButton == buttons[i][j]) {
                    row = i;
                    col = j;
                    break outerLoop;
                }
            }
        }

        if (board[row][col] == ' ') {
            if (playerXTurn) {
                buttons[row][col].setText("X");
                board[row][col] = 'X';
                statusLabel.setText("Player O's turn");
            } else {
                buttons[row][col].setText("O");
                board[row][col] = 'O';
                statusLabel.setText("Player X's turn");
            }

            if (checkForWinner()) {
                String winner = playerXTurn ? "Player X" : "Player O";
                JOptionPane.showMessageDialog(this, winner + " wins!");
                disableButtons();
                statusLabel.setText(winner + " wins!");
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a tie!");
                statusLabel.setText("It's a tie!");
            } else {
                playerXTurn = !playerXTurn;
            }
        }
    }

    private boolean checkForWinner() {
        char currentPlayer = playerXTurn ? 'X' : 'O';

        return (checkRowCol(board[0][0], board[0][1], board[0][2], currentPlayer) ||
                checkRowCol(board[1][0], board[1][1], board[1][2], currentPlayer) ||
                checkRowCol(board[2][0], board[2][1], board[2][2], currentPlayer) ||
                checkRowCol(board[0][0], board[1][0], board[2][0], currentPlayer) ||
                checkRowCol(board[0][1], board[1][1], board[2][1], currentPlayer) ||
                checkRowCol(board[0][2], board[1][2], board[2][2], currentPlayer) ||
                checkRowCol(board[0][0], board[1][1], board[2][2], currentPlayer) ||
                checkRowCol(board[0][2], board[1][1], board[2][0], currentPlayer));
    }

    private boolean checkRowCol(char c1, char c2, char c3, char player) {
        return (c1 == player) && (c2 == player) && (c3 == player);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        playerXTurn = true;
        statusLabel.setText("Player X's turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                board[i][j] = ' ';
            }
        }
    }
}
