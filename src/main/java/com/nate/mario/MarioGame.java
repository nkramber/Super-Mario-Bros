package com.nate.mario;

import java.util.List;

import com.nate.mario.entity.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.level.Level;
import com.nate.mario.state.GameState;

public class MarioGame extends GameState {

    private Player mario;
    private List<Level> levels;
    private Level currentLevel;
    private int currentLevelNumber = 0;

    public MarioGame() {
        levels = Level.loadLevels();
        currentLevel = levels.get(currentLevelNumber);

        mario = new Player(4, 11);
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
