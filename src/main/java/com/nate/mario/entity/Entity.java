package com.nate.mario.entity;

import com.nate.mario.gfx.Screen;

public class Entity {

    protected float x, y;
    protected float xDir, yDir;

    public Entity(float xTile, float yTile, float xDir, float yDir) {
        this.x = xTile * 16;
        this.y = yTile * 16;
        this.xDir = xDir;
        this.yDir = yDir;
    }

    public void tick() {}
    public void render(Screen screen) {}
}
