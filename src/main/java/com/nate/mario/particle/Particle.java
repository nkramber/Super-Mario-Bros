package com.nate.mario.particle;

import com.nate.mario.gfx.sprite.Sprite;

public class Particle {

    protected float x, y;
    protected float dirX, dirY;
    protected boolean toBeDeleted = false;

    protected static final float VER_ACCEL_RATE = 0.35f;
    protected static final float VER_MAX_SPEED = 4.0f;

    protected Sprite sprite;

    public Particle(float x, float y, float dirX, float dirY) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public void tick() {}

    public void setToBeDeleted() { toBeDeleted = true; }
    public boolean isToBeDeleted() { return toBeDeleted; }
    public Sprite getSprite() { return sprite; }
    public float getX() { return x; }
    public float getY() { return y; }
}
