public class Game {
    private TTTBoard board;
    private char currentPlayer;
    private boolean isGameOver;

    public Game() {
        board = new TTTBoard();
        currentPlayer = 'X';
        isGameOver = false;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int row, int col) {
        if (isGameOver || !board.markCell(row, col, currentPlayer)) {
            return false;
        }

        if (board.checkWinner(currentPlayer)) {
            isGameOver = true;
        } else if (board.isFull()) {
            isGameOver = true; // It's a draw
        } else {
            switchPlayer(); // Only switch player after a valid move
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void resetGame() {
        board.resetBoard();
        currentPlayer = 'X';
        isGameOver = false;
    }

    public TTTBoard getBoard() {
        return board;
    }
}
