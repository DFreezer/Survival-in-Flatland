package dev.DualKeys.SIF;

import dev.DualKeys.SIF.graphics.Assets;
import dev.DualKeys.SIF.graphics.GameCamera;
import dev.DualKeys.SIF.input.KeyManager;
import dev.DualKeys.SIF.sound.AudioMap;
import dev.DualKeys.SIF.sound.SoundThread;
import dev.DualKeys.SIF.states.GameState;
import dev.DualKeys.SIF.states.Menu;
import dev.DualKeys.SIF.states.State;
import dev.DualKeys.SIF.states.StateManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private Launcher display;
    private String title;
    private int width, height;

    private Thread thread;
    private Boolean running;

    private KeyManager keyManager;

    public static State gameState;
    public static State menuState;

    private static GameCamera gameCamera;

    private Handler handler;

    private SoundThread soundThread;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
        
        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0);

        this.start();
    }

    private void init() {
        display = new Launcher(title, width, height);
        display.getFrame().addKeyListener(keyManager);

        gameState = new GameState(handler);
        menuState = new Menu(handler);
        StateManager.setState(menuState);

        Assets.init();
        new AudioMap();
        soundThread = new SoundThread();
        soundThread.run();
    }

    private void update() {
        keyManager.update();
        StateManager.getState().update();
    }

    private void render() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        //
        StateManager.getState().render(g);
        //
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {

        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println(ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();

    }

    private synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    KeyManager getKeyManager() {
        return keyManager;
    }

    GameCamera getGameCamera() {
        return gameCamera;
    }
    
    int getHeight() {
        return height;
    }
    
    int getWidth() {
        return width;
    }
    
}