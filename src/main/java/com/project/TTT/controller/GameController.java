package com.project.TTT.controller;
import com.project.TTT.controller.dto.ConnectionRequest;
import com.project.TTT.models.game.Game;
import com.project.TTT.models.game.Player;
import com.project.TTT.services.GameService;
import com.project.TTT.storage.GameStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
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

}
