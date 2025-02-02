package com.nate.mario.entity;

import java.awt.event.KeyEvent;

import com.nate.mario.gfx.Sprite;

public class Player extends Entity {

    private static final int STARTING_SIZE = 2;
    private static final String STARTING_SPRITE = Sprite.MARIO_TALL_STILL;

    private static final float HOR_DECEL_RATE = 0.2f;
    private static final float HOR_ACCEL_RATE = 0.1f;
    private static final float HOR_MAX_SPEED = 3.0f;

    private static final float VER_ACCEL_RATE = 0.45f;
    private static final float VER_MAX_SPEED = 7.0f;

    private static final float SKID_RATE = 1.2f;
    private static final float JUMP_VELOCITY = -5.5f;

    private final int rightKey = KeyEvent.VK_D;
    private final int leftKey = KeyEvent.VK_A;
    private final int crouchKey = KeyEvent.VK_S;
    private final int jumpKey = KeyEvent.VK_SLASH;
    private final int actionKey = KeyEvent.VK_PERIOD;

    private boolean hasJumped = false;

    public Player(float xTile, float yTile) {
        super(xTile, yTile, 0, 0, 1, STARTING_SIZE, STARTING_SPRITE);
    }

    @Override
    public void getMovement(boolean[] keys) {
        if (yDir != 0) onGround = false;

        if (!onGround) {
            if (yDir + VER_ACCEL_RATE > VER_MAX_SPEED) yDir = VER_MAX_SPEED;
            else yDir += VER_ACCEL_RATE;
        }
        
        if ((!keys[leftKey] && !keys[rightKey]) || (keys[leftKey] && keys[rightKey])) {
            if (xDir > 0) {
                if (xDir - HOR_DECEL_RATE < 0) xDir = 0;
                else xDir -= HOR_DECEL_RATE;
            } else if (xDir < 0) {
                if (xDir + HOR_DECEL_RATE > 0) xDir = 0;
                else xDir += HOR_DECEL_RATE;
            }
        } else if (keys[leftKey]) {
            if (xDir > 0) {
                if (xDir - HOR_DECEL_RATE * SKID_RATE < 0) {
                    xDir = 0;
                } else {
                    if (hasJumped) xDir -= HOR_DECEL_RATE / 3;
                    else xDir -= HOR_DECEL_RATE * SKID_RATE;
                }
            } else if (xDir - HOR_ACCEL_RATE < -HOR_MAX_SPEED) {
                xDir = -HOR_MAX_SPEED;
            } else {
                if (hasJumped) xDir -= HOR_ACCEL_RATE / 3;
                else xDir -= HOR_ACCEL_RATE;
            }
        } else if (keys[rightKey]) {
            if (xDir < 0) {
                if (xDir + HOR_DECEL_RATE * SKID_RATE > 0) {
                    xDir = 0;
                } else {
                    if (hasJumped) xDir += HOR_DECEL_RATE / 3;
                    else xDir += HOR_DECEL_RATE * SKID_RATE;
                }
            } else if (xDir + HOR_ACCEL_RATE > HOR_MAX_SPEED) {
                xDir = HOR_MAX_SPEED;
            } else {
                if (hasJumped) xDir += HOR_ACCEL_RATE / 3;
                else xDir += HOR_ACCEL_RATE;
            }
        }

        if (x + xDir <= 0) {
            xDir = 0;
            x = 0.00001f;
        }

        if (keys[jumpKey] && !hasJumped && onGround) {
            onGround = false;
            hasJumped = true;
            yDir = JUMP_VELOCITY;
        }

        if (!keys[jumpKey]) hasJumped = false;
    }

    public void setHeight(int tiles) {
        height = tiles * Sprite.TILE_HEIGHT;
    }
}
