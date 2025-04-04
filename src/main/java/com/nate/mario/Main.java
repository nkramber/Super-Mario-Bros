package com.nate.mario;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.nate.mario.gfx.Screen;
import com.nate.mario.state.GameState;
import com.nate.mario.state.MarioGame;

public class Main extends Canvas implements Runnable, KeyListener {

    public static final int SCREEN_WIDTH = 256;
    public static final int SCREEN_HEIGHT = 224;
    private static final int SCREEN_SCALE = 3;
    private static final String TITLE = "Super Mario Bros.";
    private static final double TARGET_FPS = 60.0;
    private static final double TIME_BETWEEN_FRAMES = 1000000000 / TARGET_FPS;

    private static final int ENTER_KEY = KeyEvent.VK_ENTER;
    
    private static JFrame FRAME;
    private static Random RANDOM;
    private static int TICKS = 0;
    
    private BufferedImage display;
    private GameState currentState;
    private Screen screen;
    
    private boolean enterKeyPressed = false;
    private boolean paused = false;
    private boolean running = true;
    private boolean keys[];

    private void init() {
        display = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        RANDOM = new Random();
        keys = new boolean[256];

        screen = new Screen((Graphics2D) display.getGraphics());
        //draw "load screen" here
        currentState = new MarioGame(screen);

        addKeyListener(this);
    }

    @Override
    public void run() {
        init();

        double lastUpdateTime = System.nanoTime();
        while(running) {
            double currentTime = System.nanoTime();
            while (currentTime - lastUpdateTime > TIME_BETWEEN_FRAMES) {
                // measureFPS();
                tick();
                render();
                lastUpdateTime += TIME_BETWEEN_FRAMES;
            }
        }
        System.exit(0);
    }

    // private long lastFPSCheck;
    // private int currentFPS = 0;
    // private int frameCount = 0;
    //
    // private void measureFPS() {
    //     frameCount++;
    //     if(System.currentTimeMillis() - lastFPSCheck >= 1000) {
    //         currentFPS = frameCount;
    //         if (currentFPS > 3) System.out.println("FPS: " + currentFPS);
    //         frameCount = 0;
    //         lastFPSCheck = System.currentTimeMillis();
    //     }
    // }

    private void tick() {
        checkPauseKeyState();

        if (paused) {
            //This prevents the player's run animation from going too fast after returning from pause
            currentState.adjustSpriteTimerWhenPaused();
            return;
        }

        TICKS++;
        currentState.tick(keys);
    }

    private void render() {
        Graphics2D g = screen.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        currentState.render();

        getGraphics().drawImage(display, 0, 0, SCREEN_WIDTH * SCREEN_SCALE, SCREEN_HEIGHT * SCREEN_SCALE, this);
    }

    private void start() {
        super.requestFocus();
        new Thread(this).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setMinimumSize(new Dimension(SCREEN_WIDTH * SCREEN_SCALE, SCREEN_HEIGHT * SCREEN_SCALE));
            main.setMaximumSize(new Dimension(SCREEN_WIDTH * SCREEN_SCALE, SCREEN_HEIGHT * SCREEN_SCALE));
            main.setPreferredSize(new Dimension(SCREEN_WIDTH * SCREEN_SCALE, SCREEN_HEIGHT * SCREEN_SCALE));

            FRAME = new JFrame(TITLE);
            FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            FRAME.setLayout(new BorderLayout());
            FRAME.add(main);
            FRAME.pack();
            FRAME.setResizable(false);
            FRAME.setLocationRelativeTo(null);
            FRAME.setVisible(true);
            FRAME.setIgnoreRepaint(true);

            main.start();
        });
    }

    private void checkPauseKeyState() {
        if (keys[ENTER_KEY] && !enterKeyPressed) {
            enterKeyPressed = true;
            paused = !paused;
        } else if (!keys[ENTER_KEY]) {
            enterKeyPressed = false;
        }
    }

    public void setState(GameState state) { currentState = state; }
    public void setNotRunning() { running = false; }

    public static Random getRandom() { return RANDOM; }
    public static int getTickCount() { return TICKS; }
    
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
    @Override public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
}
