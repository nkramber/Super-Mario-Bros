package com.nate.mario.state;

import com.nate.mario.Main;
import com.nate.mario.gfx.Screen;

public class MarioGame extends GameState {

    @Override
    public void tick(Main main) {

    }

    @Override
    public void render(Screen screen) {
        screen.drawSprite("small_maio_run", 100, 100);
        screen.drawSprite("small_mario_still", 50, 50);
    }
}
