package com.project.TTT.controller;

import com.project.TTT.controller.dto.ConnectionRequest;
import com.project.TTT.models.game.Game;
import com.project.TTT.models.game.Player;
import com.project.TTT.services.GameService;
import com.project.TTT.services.boards.UtttMethods;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
@CrossOrigin("http://localhost:4200")
public class GameController {
    private final GameService gameService;
    private final UtttMethods methods;
    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player){
        return ResponseEntity.ok(gameService.createGame(player));
    }
    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectionRequest request) throws Exception {
        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(), request.getGameId()));
    }
    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectToRandom(@RequestBody Player player){
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }
    ///TODO: player switch in game

    ///TODO: request for model changing
    @PostMapping("/move")
    public ResponseEntity<String> getMove(@RequestBody String positions){
        return ResponseEntity.ok("pos: "+positions);
    }
}
