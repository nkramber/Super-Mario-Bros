package com.nate.mario;

import com.nate.mario.gfx.Screen;
import com.nate.mario.state.GameState;

public class MarioGame extends GameState {

    @Override
    public void tick(Main main) {

    }

    @Override
    public void render(Screen screen) {
        screen.drawSprite("small_mario_run2", Main.getRandom().nextInt(Main.SCREEN_WIDTH - 16), Main.getRandom().nextInt(Main.SCREEN_HEIGHT - 16));
    }
}
