package com.project.TTT.models.boards;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class TttBoard {
    private char player1;
    private char player2;
    private char emptySquare;
    private Map<String, Character> position;
    public TttBoard() {
        this.player1 = 'x';
        this.player2 = 'o';
        this.emptySquare = '.';

        // Initialize the board
        this.position = new HashMap<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.position.put(row + "," + col, this.emptySquare);
            }
        }
    }
    public TttBoard(TttBoard board){
        this.player1 = board.player1;
        this.player2 = board.player2;
        this.emptySquare = board.emptySquare;

        this.position = new HashMap<>();
        this.position.putAll(board.position);
    }

}
