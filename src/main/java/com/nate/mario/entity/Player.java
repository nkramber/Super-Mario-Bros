package com.nate.mario.entity;

import java.awt.event.KeyEvent;

import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.Sprite;

public class Player extends Entity {

    private static final float HOR_DECEL_RATE = 0.2f;
    private static final float HOR_ACCEL_RATE = 0.06f;
    private static final float HOR_MAX_SPEED = 3.0f;

    private static final float VER_ACCEL_RATE = 0.3f;
    private static final float VER_MAX_SPEED = 5.0f;
    private static final float JUMP_VELOCITY = -3.5f;

    private final int rightKey = KeyEvent.VK_D;
    private final int leftKey = KeyEvent.VK_A;
    private final int crouchKey = KeyEvent.VK_S;
    private final int jumpKey = KeyEvent.VK_SLASH;
    private final int actionKey = KeyEvent.VK_PERIOD;

    private String currentSprite = Sprite.MARIO_SMALL_STILL;
    private int width = 1 * Sprite.TILE_WIDTH;
    private int height = 1 * Sprite.TILE_HEIGHT;
    private boolean onGround = true;

    public Player(float xTile, float yTile) {
        super(xTile, yTile, 0, 0);
    }

    public void tick(boolean[] keys) {
        x += xDir;
        y += yDir;

        if (!onGround) {
            if (yDir + VER_ACCEL_RATE > VER_MAX_SPEED) yDir = VER_MAX_SPEED;
            else yDir += VER_ACCEL_RATE;
        }

        if (!keys[leftKey] && !keys[rightKey]) {
            if (xDir > 0) {
                if (xDir - HOR_DECEL_RATE < 0) xDir = 0;
                else xDir -= HOR_DECEL_RATE;
            } else if (xDir < 0) {
                if (xDir + HOR_DECEL_RATE > 0) xDir = 0;
                else xDir += HOR_DECEL_RATE;
            }
        }

        if (keys[leftKey]) {
            if (xDir - HOR_ACCEL_RATE < -HOR_MAX_SPEED) xDir = -HOR_MAX_SPEED;
            else xDir -= HOR_ACCEL_RATE;
        }

        if (keys[rightKey]) {
            if (xDir + HOR_ACCEL_RATE > HOR_MAX_SPEED) xDir = HOR_MAX_SPEED;
            else xDir += HOR_ACCEL_RATE;
        }

        if (keys[jumpKey] && onGround) {
            onGround = false;
            yDir = JUMP_VELOCITY;
        }

        System.out.println(xDir + ", " + yDir);
    }

    public void render(Screen screen) {
        screen.drawSprite(currentSprite, (int) x, (int) y);
    }

    public void setHeight(int tiles) {
        this.height = tiles * Sprite.TILE_HEIGHT;
    }
}
