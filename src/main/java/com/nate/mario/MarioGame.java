package com.nate.mario;

import java.util.HashMap;

import com.nate.mario.entity.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.level.Level;
import com.nate.mario.level.LevelLoader;
import com.nate.mario.state.GameState;

public class MarioGame extends GameState {

    private Player mario;
    private HashMap<Integer, Level> levels;
    private Level currentLevel;
    private int currentLevelNumber = 0;

    public MarioGame() {
        levels = LevelLoader.loadLevels();
        currentLevel = levels.get(currentLevelNumber);

        mario = new Player(4, 10);
        currentLevel.addPlayer(mario);
    }

    @Override
    public void tick(boolean[] keys) {
        currentLevel.tick(this, keys);
    }

    @Override
    public void render(Screen screen) {
        currentLevel.render(screen);
    }
}
