package dev.DualKeys.SIF;

import dev.DualKeys.SIF.graphics.GameCamera;
import dev.DualKeys.SIF.input.KeyManager;
import dev.DualKeys.SIF.worlds.World;

public class Handler {

    private Game game;
    private World world;
    
    public Handler(Game game) {
        this.game = game;
    }
    
    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }
    
    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }
    
    public int getHeight() {
        return game.getHeight();
    }
    
    public int getWidth() {
        return game.getWidth();
    }
    
    public Game getGame() {
        return game;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    public World getWorld() {
        return world;
    }
    
    public void setWorld(World world) {
        this.world = world;
    }
    
}