package com.project.TTT.services.boards;

import com.project.TTT.models.boards.UtttBoard;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UtttMethods {
    public  List<int[]> generateStates(UtttBoard board) {
        List<int[]> actions = new ArrayList<>();
        int[] lastMove = board.getLastMove();

        // Check if it's the first move
        if (lastMove == null || (lastMove[0] == 0 && lastMove[1] == 0 && lastMove[2] == 0 && lastMove[3] == 0)) {
            // Allow moves in any small square of the entire board
            for (int bigRow = 0; bigRow < 3; bigRow++) {
                for (int bigCol = 0; bigCol < 3; bigCol++) {
                    states(board, actions, bigRow, bigCol);
                }
            }
        } else {
            int bigRow = lastMove[2];
            int bigCol = lastMove[3];
            if (board.getMainBoard()[bigRow][bigCol].equals(board.getEmptySquare())) {
                states(board, actions, bigRow, bigCol);
            } else {
                for (int bigRowIter = 0; bigRowIter < 3; bigRowIter++) {
                    for (int bigColIter = 0; bigColIter < 3; bigColIter++) {
                        if (board.getMainBoard()[bigRowIter][bigColIter].equals(board.getEmptySquare())) {
                            states(board, actions, bigRowIter, bigColIter);
                        }
                    }
                }
            }
        }
        return actions;
    }

    public static void states(UtttBoard board, List<int[]> actions, int bigRow, int bigCol) {
        for (int smallRow = 0; smallRow < 3; smallRow++) {
            for (int smallCol = 0; smallCol < 3; smallCol++) {
                if (board.getSubBoards()[bigRow][bigCol][smallRow][smallCol].equals(board.getEmptySquare())) {
                    actions.add(new int[]{bigRow, bigCol, smallRow, smallCol});
                }
            }
        }
    }

    public UtttBoard makeMove(UtttBoard board,int bigRow, int bigCol, int smallRow, int smallCol) {
        // Create a deep copy of the current board
        UtttBoard newBoard = new UtttBoard(board); // Assuming a copy constructor is defined

        // Make the move
        newBoard.getSubBoards()[bigRow][bigCol][smallRow][smallCol] = newBoard.getPlayer1();

        // Check for win or draw
        if (isWin(newBoard.getSubBoards()[bigRow][bigCol],newBoard.getPlayer1())){
            newBoard.getMainBoard()[bigRow][bigCol] = newBoard.getPlayer1();
        } else if (isDraw(newBoard.getSubBoards()[bigRow][bigCol],newBoard.getEmptySquare())) {
            newBoard.getMainBoard()[bigRow][bigCol] = "x/o";
        }

        // Swap players
        String tempPlayer = newBoard.getPlayer1();
        newBoard.setPlayer1(newBoard.getPlayer2());
        newBoard.setPlayer2(tempPlayer);

        // Update last move
        newBoard.setLastMove(new int[]{bigRow, bigCol, smallRow, smallCol});

        return newBoard;
    }
    public  boolean isDraw(String[][] board,String emptySquare) {
        for (String[] strings : board) {
            for (String string : strings) {
                if (string.equals(emptySquare)) {
                    return false;
                }
            }
        }
        return true;
    }
    public  boolean isWin(String[][] board, String playerSymbol) {
        // Check each column for a win
        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(playerSymbol) && board[1][col].equals(playerSymbol) && board[2][col].equals(playerSymbol)) {
                return true;
            }
        }

        // Check each row for a win
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(playerSymbol) && board[row][1].equals(playerSymbol) && board[row][2].equals(playerSymbol)) {
                return true;
            }
        }

        // Check diagonals for a win
        if (board[0][0].equals(playerSymbol) && board[1][1].equals(playerSymbol) && board[2][2].equals(playerSymbol)) {
            return true;
        }

        if (board[0][2].equals(playerSymbol) && board[1][1].equals(playerSymbol) && board[2][0].equals(playerSymbol)) {
            return true;
        }

        return false;
    }
}
