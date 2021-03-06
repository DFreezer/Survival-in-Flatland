package dev.DualKeys.SIF.states;

import dev.DualKeys.SIF.Handler;
import dev.DualKeys.SIF.UI.GUI;
import dev.DualKeys.SIF.entities.creatures.Player;
import dev.DualKeys.SIF.graphics.Assets;
import dev.DualKeys.SIF.worlds.World;

import java.awt.*;

public class GameState extends State {

    private Player player;
    private GUI gui;
    private World world;
	
    public GameState(Handler handler) {
	super(handler);
        Assets.init();
        world = new World(handler, "res/Worlds/world1.world");
        handler.setWorld(world);
	    player = new Player(handler, 32, 32);
        gui = new GUI(handler, player);
    }
	
    @Override
    public void update() {
        world.update();
	player.update();
        gui.update();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
	player.render(g);
        gui.render(g);
    }

}
