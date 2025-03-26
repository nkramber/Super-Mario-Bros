package com.nate.mario.particle;

import com.nate.mario.Main;
import com.nate.mario.gfx.sprite.Sprite;

public class Particle {

    protected float x, y;
    protected float dirX, dirY;
    protected boolean toBeDeleted = false;

    private static final float VER_ACCEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 4.0f;

    private final Sprite sprite;

    public Particle(float x, float y, float dirX, float dirY, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.sprite = sprite;
    }

    public void tick() {
        if (dirY + VER_ACCEL_RATE > VER_MAX_SPEED) dirY = VER_MAX_SPEED;
        else dirY += VER_ACCEL_RATE;

        x += dirX;
        y += dirY;
    }

    public void setToBeDeleted() { toBeDeleted = true; }
    public boolean isToBeDeleted() { return toBeDeleted; }
    public Sprite getSprite() { return sprite; }
    public float getX() { return x; }
    public float getY() { return y; }
}
