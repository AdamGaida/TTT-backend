package com.project.TTT.services;

import com.project.TTT.models.boards.UtttBoard;
import com.project.TTT.models.game.Game;
import com.project.TTT.models.game.GameStatus;
import com.project.TTT.models.game.Player;
import com.project.TTT.storage.GameStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class GameService {
    public Game createGame(Player player){
        Game game = new Game();
        game.setBoard(new UtttBoard());
        game.setId(UUID.randomUUID().toString());
        game.setGameStatus(GameStatus.NEW);
        game.setPlayer1(player);
        GameStorage.getInstance().setGame(game);
        return game;
    }
    public Game connectToGame(Player player, String id) throws Exception {
        if(!GameStorage.getInstance().getGames().containsKey(id)){
            throw new NoSuchElementException("Game not found !");
        }
            Game game = GameStorage.getInstance().getGames().get(id);
            if(game.getBoard().getPlayer2() != null){
                throw new Exception("Game is not valid anymore");
            }
            game.setPlayer2(player);
            game.setGameStatus(GameStatus.IN_PRORESS);
            GameStorage.getInstance().setGame(game);
        return game;
    }
    public Game connectToRandomGame(Player player){
        Game game =GameStorage.getInstance().getGames().values().
                stream().filter(it -> it.getGameStatus().equals(GameStatus.NEW))
                .findFirst().orElseThrow(()->new NoSuchElementException("GAME NOT FOUND"));
        game.setPlayer2(player);
        game.setGameStatus(GameStatus.IN_PRORESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

}
