package com.nate.mario.state;

public abstract class GameState {

    public abstract void tick(boolean[] keys);
    public abstract void render();
    public void adjustSpriteTimerWhenPaused() {}
}
