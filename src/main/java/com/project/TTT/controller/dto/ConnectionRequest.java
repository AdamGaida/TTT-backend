package com.project.TTT.controller.dto;

import com.project.TTT.models.game.Player;
import lombok.Data;

@Data
public class ConnectionRequest {
    private Player player;
    private String gameId;
}
