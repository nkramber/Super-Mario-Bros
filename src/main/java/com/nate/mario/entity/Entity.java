package com.nate.mario.entity;

import com.nate.mario.gfx.Screen;

public class Entity {

    protected float x, y;

    public Entity(float xTile, float yTile) {
        this.x = xTile * 16;
        this.y = yTile * 16;
    }

    public void tick() {}
    public void render(Screen screen) {}
}
