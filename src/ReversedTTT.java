import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ReversedTTT extends JFrame implements ActionListener {
    private final char[][] board;
    private char currentPlayer;
    private boolean gameEnd;
    private final Difficulty difficulty;
    private final JButton[][] buttons;

    enum Difficulty {
        EASY, MEDIUM, HARD
    }

//    public ReversedTTT() {
//        this(Difficulty.EASY); // Set default difficulty
//    }

    public ReversedTTT(int difficulty) {
        this.board = new char[3][3];
        this.currentPlayer = 'X';
        this.gameEnd = false;
        if(difficulty==1)
            this.difficulty=Difficulty.EASY;
        else if (difficulty==2)
            this.difficulty =Difficulty.MEDIUM;
        else
            this.difficulty=Difficulty.HARD;
        this.buttons = new JButton[3][3];
        initializeBoard();
        createGUI();
    }

    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = ' ';
            }
        }
    }

    private void createGUI() {
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        boardPanel.setBackground(Color.BLACK);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton("");
                button.setFont(new Font("Ink Free", Font.BOLD, 60));
                button.setPreferredSize(new Dimension(200, 200));
                button.addActionListener(this);
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("Reversed Tic Tac");
        title.setFont(new Font("Ink Free", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(JLabel.CENTER);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Ink Free", Font.BOLD, 24));
        newGameButton.addActionListener(this);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.add(title);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(newGameButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Reversed Tic-Tac-Toe");
        setResizable(false);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("") && !gameEnd && currentPlayer == 'X') {
                int row = -1, col = -1;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (button == buttons[i][j]) {
                            row = i;
                            col = j;
                            break;
                        }
                    }
                }
                makeMove(row, col);
            } else if (button.getText().equals("New Game")) {
                resetGame();
            }
        }
    }

    private void makeMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
            board[row][col] = currentPlayer;

            JButton button = buttons[row][col];
            button.setEnabled(false);

            JLabel label = new JLabel(Character.toString(currentPlayer));
            label.setFont(new Font("Ink Free", Font.BOLD, 60));
            label.setForeground(currentPlayer == 'X' ? Color.RED : Color.GREEN);

            Dimension buttonSize = button.getSize();
            Dimension labelSize = label.getPreferredSize();
            int x = (buttonSize.width - labelSize.width) / 2;
            int y = (buttonSize.height - labelSize.height) / 2;
            label.setBounds(x, y, labelSize.width, labelSize.height);

            button.setLayout(null);
            button.add(label);


            if (currentPlayer == 'X') {
                buttons[row][col].setBackground(Color.RED);
            } else {
                buttons[row][col].setBackground(Color.GREEN);
            }


            if (checkWin()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                gameEnd = true;
                return;
            }

            if (checkDraw()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                gameEnd = true;
                return;
            }

            switchPlayer();

            if (currentPlayer == 'O' && !gameEnd) {
                computerMove();
            }
        }
    }
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private boolean checkWin() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == 'X' && board[row][1] == 'X' && board[row][2] == 'X') {
                if (currentPlayer == 'O') {
                    return true;
                } else {
                    return false;
                }
            } else if (board[row][0] == 'O' && board[row][1] == 'O' && board[row][2] == 'O') {
                if (currentPlayer == 'X') {
                    return true;
                } else {
                    return false;
                }
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == 'X' && board[1][col] == 'X' && board[2][col] == 'X') {
                if (currentPlayer == 'O') {
                    return true;
                } else {
                    return false;
                }
            } else if (board[0][col] == 'O' && board[1][col] == 'O' && board[2][col] == 'O') {
                if (currentPlayer == 'X') {
                    return true;
                } else {
                    return false;
                }
            }
        }

        // Check diagonals
        if ((board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X') ||
                (board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] == 'X')) {
            if (currentPlayer == 'O') {
                return true;
            } else {
                return false;
            }
        } else if ((board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O') ||
                (board[0][2] == 'O' && board[1][1] == 'O' && board[2][0] == 'O')) {
            if (currentPlayer == 'X') {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private boolean checkDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void computerMove() {
        switch (difficulty) {
            case EASY:
                makeRandomMove();
                break;
            case MEDIUM:
                makeMediumMove();
                break;
            case HARD:
                makeBestMove();
                break;
        }
    }

    private void makeRandomMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != ' ');
        makeMove(row, col);
    }

    private void makeMediumMove() {
        // First, check if there is a winning move for the computer
        int[] winningMove = getWinningMove('O');
        if (winningMove != null) {
            makeMove(winningMove[0], winningMove[1]);
            return;
        }

        // If no winning move, check if there is a blocking move for the opponent
        int[] blockingMove = getWinningMove('X');
        if (blockingMove != null) {
            makeMove(blockingMove[0], blockingMove[1]);
            return;
        }

        // If no winning or blocking move, make a random move
        makeRandomMove();
    }

    private void makeBestMove() {
        int[] bestMove = getBestMove();
        makeMove(bestMove[0], bestMove[1]);
    }

    private int[] getWinningMove(char player) {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == ' ') {
                return new int[]{row, 2};
            }
            if (board[row][0] == player && board[row][2] == player && board[row][1] == ' ') {
                return new int[]{row, 1};
            }
            if (board[row][1] == player && board[row][2] == player && board[row][0] == ' ') {
                return new int[]{row, 0};
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == ' ') {
                return new int[]{2, col};
            }
            if (board[0][col] == player && board[2][col] == player && board[1][col] == ' ') {
                return new int[]{1, col};
            }
            if (board[1][col] == player && board[2][col] == player && board[0][col] == ' ') {
                return new int[]{0, col};
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == ' ') {
            return new int[]{2, 2};
        }
        if (board[0][0] == player && board[2][2] == player && board[1][1] == ' ') {
            return new int[]{1, 1};
        }
        if (board[1][1] == player && board[2][2] == player && board[0][0] == ' ') {
            return new int[]{0, 0};
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == ' ') {
            return new int[]{2, 0};
        }
        if (board[0][2] == player && board[2][0] == player && board[1][1] == ' ') {
            return new int[]{1, 1};
        }
        if (board[2][0] == player && board[1][1] == player && board[0][2] == ' ') {
            return new int[]{0, 2};
        }

        return null;
    }

    private int[] getBestMove() {
        // Implement your logic to determine the best move for the computer
        // This can involve using algorithms like Minimax or heuristics to evaluate the best move
        // Here, I'll just make a random move for demonstration purposes
        return getRandomEmptyCell();
    }

    private int[] getRandomEmptyCell() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != ' ');
        return new int[]{row, col};
    }

    private void resetGame() {
        gameEnd = false;
        currentPlayer = 'X';
        initializeBoard();
        clearBoardButtons();
    }

    private void clearBoardButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
            ReversedTTT game = new ReversedTTT(3);
        });
    }
}
