package com.nate.mario.state;

import com.nate.mario.gfx.Screen;

public abstract class GameState {

    public abstract void tick(boolean[] keys);
    public abstract void render(Screen screen);
}
