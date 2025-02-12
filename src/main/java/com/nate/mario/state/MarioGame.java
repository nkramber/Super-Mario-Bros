package com.nate.mario.state;

import java.util.HashMap;

import com.nate.mario.entity.player.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.level.Level;
import com.nate.mario.level.LevelLoader;
import com.nate.mario.util.Timer;

public class MarioGame extends GameState {

    private Player player;
    private HashMap<Integer, Level> levels;
    private Level currentLevel;
    private int currentLevelNumber;

    private boolean gameOver = false;
    private Timer gameOverTimer;

    private Screen screen;

    public MarioGame(Screen screen) {
        this.screen = screen;
        player = new Player(0, 0, 0);

        levels = LevelLoader.loadLevels();
        setLevel(0);
    }

    @Override
    public void tick(boolean[] keys) {
        if (gameOver) {
            gameOverTimer.tick();
        } else if (currentLevel.isGameOver()) {
            setGameOver();
        } else if (currentLevel.resetLevel()) {
            // System.out.println(currentLevel.getPlayerLives()); // Determine why lives reset to 2
            setLevel(currentLevelNumber);
        } else {
            currentLevel.tick(keys);

            if (currentLevel.isLevelFinished()) {
                if (currentLevelNumber + 1 < levels.size()) {
                    setLevel(currentLevelNumber + 1);
                } else {
                    setGameOver(); //change to "win" condition later
                }
            }
        }
    }

    private void setGameOver() {
        gameOver = true;
        gameOverTimer = new Timer();
    }

    private void setLevel(int levelNumber) {
        Level levelToGet = levels.get(levelNumber);
        currentLevel = levelToGet.newLevel();
        player = new Player(currentLevel.getPlayerSpawnX(), currentLevel.getPlayerSpawnY(), player.getScore());
        currentLevel.addPlayer(player);
        screen.resetScroll();
    }

    private void newGame() {
        levels = LevelLoader.loadLevels();
        setLevel(0);
    }

    @Override
    public void render() {
        if (gameOver) {
            if (gameOverTimer.getElapsedTime() < 7000) {
                screen.drawGameOver(player.getCoinCount(), player.getScore(), currentLevel.getLevelName());
            } else {
                gameOver = false;
                gameOverTimer = null;
                newGame();
            }
        }
        else currentLevel.render(screen);
    }
}
