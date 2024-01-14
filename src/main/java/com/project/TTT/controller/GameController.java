package com.project.TTT.controller;

import com.project.TTT.controller.dto.ConnectionRequest;
import com.project.TTT.controller.dto.TwoPlayerResponse;
import com.project.TTT.models.game.Game;
import com.project.TTT.models.game.Player;
import com.project.TTT.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/game")
@CrossOrigin("http://localhost:4200")
public class GameController {
    private final GameService gameService;
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
    @PostMapping("/2pmove")
    public ResponseEntity<TwoPlayerResponse> getMove(@RequestBody String positions){
        gameService.twoPlayerMode(positions);
        String s = gameService.getWinner();
        TwoPlayerResponse response = new TwoPlayerResponse(gameService.getBoard().getMainBoard(), s);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/restart")
    public ResponseEntity<String> restart(){
        return ResponseEntity.ok(gameService.restart());
    }
    @PostMapping("/winboard")
    public ResponseEntity<String[][]> winStatus(){
        return ResponseEntity.ok(gameService.getBoard().getMainBoard());
    }

}
