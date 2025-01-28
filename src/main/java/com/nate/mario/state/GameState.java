package com.nate.mario.state;

import com.nate.mario.Main;
import com.nate.mario.gfx.Screen;

public abstract class GameState {

    public abstract void tick(Main main);
    public abstract void render(Screen screen);
}
