package com.project.TTT.services.boards;

import com.project.TTT.models.boards.TttBoard;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TttMethods {
    TttBoard board = new TttBoard();
    public TttBoard makeMove(int row, int col) {
        // Create a new TttBoard instance that inherits from the current state
        TttBoard board = new TttBoard(this.board);

        // Make the move
        board.getPosition().put(row + "," + col, this.board.getPlayer1());

        // Swap players
        char temp = board.getPlayer1();
        board.setPlayer1(board.getPlayer2());
        board.setPlayer2(temp);

        // Return the new board state
        return board;
    }
    public boolean isDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                // Check if the square is empty
                if (board.getPosition().get(row + "," + col).equals(board.getEmptySquare())) {
                    // This is not a draw
                    return false;
                }
            }
        }
        // By default, return true indicating a draw
        return true;
    }
    public boolean isWin() {
        char currentPlayer = board.getPlayer2();

        // Vertical sequence detection
        for (int col = 0; col < 3; col++) {
            if (board.getPosition().get("0," + col) == currentPlayer &&
                    board.getPosition().get("1," + col) == currentPlayer &&
                    board.getPosition().get("2," + col) == currentPlayer) {
                return true;
            }
        }

        // Horizontal sequence detection
        for (int row = 0; row < 3; row++) {
            if (board.getPosition().get(row + ",0") == currentPlayer &&
                    board.getPosition().get(row + ",1") == currentPlayer &&
                    board.getPosition().get(row + ",2") == currentPlayer) {
                return true;
            }
        }

        // 1st Diagonal sequence detection
        if (board.getPosition().get("0,0") == currentPlayer &&
                board.getPosition().get("1,1") == currentPlayer &&
                board.getPosition().get("2,2") == currentPlayer) {
            return true;
        }

        // 2nd Diagonal sequence detection
        if (board.getPosition().get("0,2") == currentPlayer &&
                board.getPosition().get("1,1") == currentPlayer &&
                board.getPosition().get("2,0") == currentPlayer) {
            return true;
        }

        // By default, return non-winning state
        return false;
    }
    public List<TttBoard> generateStates() {
        List<TttBoard> actions = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                // Check if the current square is empty
                if (this.board.getPosition().get(row + "," + col) == this.board.getEmptySquare()) {
                    // Append available action/board state to actions list
                    actions.add(this.makeMove(row, col));
                }
            }
        }

        // Return the list of available actions (board class instances)
        return actions;
    }

}
