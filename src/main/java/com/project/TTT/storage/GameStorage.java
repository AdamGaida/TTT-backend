package com.project.TTT.storage;

import com.project.TTT.models.game.Game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameStorage {
    private static volatile GameStorage instance;
    private final Map<String, Game> games;
    private GameStorage(){
        this.games = new HashMap<>();
    }
    public static GameStorage getInstance() {
        if (instance == null) {
            synchronized (GameStorage.class) {
                if (instance == null) {
                    instance = new GameStorage();
                }
            }
        }
        return instance;
    }
    public Map<String, Game> getGames() {
        return Collections.unmodifiableMap(games);
    }
    public void setGame(Game game) {
        games.put(game.getId(), game);
    }


}
