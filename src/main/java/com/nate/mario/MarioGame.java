package com.nate.mario;

import com.nate.mario.entity.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.level.Level;
import com.nate.mario.state.GameState;

public class MarioGame extends GameState {

    private Player mario;
    private Level currentLevel;
    private Level[] levels;

    public MarioGame() {   
        mario = new Player(5, 10);

        levels = new Level[Level.getLevelCount()];
        
    }

    @Override
    public void tick(boolean[] keys) {
        mario.tick(keys);
    }

    @Override
    public void render(Screen screen) {
        mario.render(screen);
    }
}
