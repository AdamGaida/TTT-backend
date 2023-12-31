package com.project.TTT.models.game;

import com.project.TTT.models.boards.UtttBoard;
import lombok.Data;

@Data
public class Game {
    private String id;
    private UtttBoard board;
}
