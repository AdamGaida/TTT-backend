package com.project.TTT.services;

import com.project.TTT.models.boards.UtttBoard;
import com.project.TTT.models.game.Game;
import com.project.TTT.models.game.Player;
import com.project.TTT.storage.GameStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class GameService {
    public Game createGame(Player player){
        Game game = new Game();
        game.setBoard(new UtttBoard());
        game.setId(UUID.randomUUID().toString());
        return game;
    }
    public Game connectToGame(Player player, String id){
        if(!GameStorage.getInstance().getGames().containsKey(id)){
            throw new NoSuchElementException("Game not found !");
        }
            Game game = GameStorage.getInstance().getGames().get(id);
            GameStorage.getInstance().setGame(game);
        return game;
    }
    public Game connectToRandomGame(){
        return null;
    }

}
