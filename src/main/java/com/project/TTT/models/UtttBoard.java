package com.project.TTT.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
public class UtttBoard {
    private String player1;
    private String player2;
    private String emptySquare;
    public String[][][][] subBoards;
    private String[][] mainBoard;
    private int[] lastMove;
    public UtttBoard(String player1) {
        this.player1 = player1;
        this.player2 = Objects.equals(player1, "x") ? "o" : "x";
        this.emptySquare = ".";
        this.subBoards = new String[3][3][3][3];
        this.mainBoard = new String[3][3];
        this.lastMove = new int[2];

        // Initialize subBoards and mainBoard with emptySquare
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    Arrays.fill(this.subBoards[i][j][k], this.emptySquare);
                }
                Arrays.fill(this.mainBoard[i], this.emptySquare);
            }
        }
    }
    public UtttBoard(UtttBoard other) {
        this.player1 = other.player1;
        this.player2 = other.player2;
        this.emptySquare = other.emptySquare;

        // Deep copy of subBoards and mainBoard
        this.subBoards = Arrays.stream(other.subBoards)
                .map(sb1 -> Arrays.stream(sb1)
                        .map(sb2 -> Arrays.stream(sb2)
                                .map(String[]::clone)
                                .toArray(String[][]::new))
                        .toArray(String[][][]::new))
                .toArray(String[][][][]::new);

        this.mainBoard = Arrays.stream(other.mainBoard)
                .map(String[]::clone)
                .toArray(String[][]::new);

        this.lastMove = other.lastMove.clone();
    }


}
