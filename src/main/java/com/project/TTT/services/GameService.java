package com.project.TTT.services;

import com.project.TTT.models.boards.UtttBoard;
import com.project.TTT.models.game.Game;
import com.project.TTT.models.game.GameStatus;
import com.project.TTT.models.game.Player;
import com.project.TTT.models.mct.TreeNode;
import com.project.TTT.services.boards.UtttMethods;
import com.project.TTT.services.mcts.UtttMcts;
import com.project.TTT.storage.GameStorage;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Data
@Service
public class GameService {
    private UtttBoard board = new UtttBoard();
    private UtttMcts utttMcts = new UtttMcts();
    private UtttMethods methods = new UtttMethods();
    private String winner = ".";
    private Map<String, String> mp = new HashMap<>();
    private int play = 0;
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
    public String switchPlayer(int count){
         return (count%2==0)?"x":"o";
    }

    ///TODO : front, back connection
    public void twoPlayerMode(String s){
        play++;
        board = methods.makeMove(board, Character.getNumericValue(s.charAt(0)),
                Character.getNumericValue(s.charAt(1)),Character.getNumericValue(s.charAt(2)),
                Character.getNumericValue(s.charAt(3)));
        ///TODO : addouma ras thouma kifee nchoufou l win fl subboard ??
        if (methods.isWin(board.getMainBoard(), switchPlayer(play))){
            winner= switchPlayer(play);
        }
        else if (methods.isDraw(board.getMainBoard(), ".")){
            winner= "x/o";
        }
        else winner= ".";
    }
    public void onePlayerMode(String s){
        play++;
        board = methods.makeMove(board, Character.getNumericValue(s.charAt(0)),
                Character.getNumericValue(s.charAt(1)),Character.getNumericValue(s.charAt(2)),
                Character.getNumericValue(s.charAt(3)));

        if (methods.isWin(board.getMainBoard(), switchPlayer(play))){
            winner = switchPlayer(play);
        }
        else if (methods.isDraw(board.getMainBoard(), switchPlayer(play))){
            winner = "x/o";
        }
        else winner= ".";
        TreeNode best_move = utttMcts.search(board);
        board = best_move.getUtttBoard();
        play++;
        int[] move = board.getLastMove();
        if (methods.isWin(board.getMainBoard(), switchPlayer(play))){
            winner = switchPlayer(play);
        }
        else if (methods.isDraw(board.getMainBoard(), switchPlayer(play))){
            winner = "x/o";
        }
        else winner= ".";
    }
    public String restart(){
        board = new UtttBoard();
        return "game restarted";
    }

}
