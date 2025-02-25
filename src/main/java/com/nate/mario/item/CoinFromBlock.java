package com.nate.mario.item;

import com.nate.mario.entity.player.Player;
import com.nate.mario.gfx.sprite.ItemSprite;

public class CoinFromBlock extends Item {

    private static float VER_ACCEL_RATE = Player.VER_ACCEL_RATE;
    private static float VELOCITY_TO_DELETE = 4.5f;

    private int animationFrame = 0;
    private float yDir = -5.5f;

    public CoinFromBlock(float x, float y) {
        super(x, y, ItemSprite.ANIMATED_COIN);
    }

    public void updateAnimationFrame() {
        animationFrame++;
        if (animationFrame == 8) animationFrame = 0;
    }

    public void move() {
        yDir += VER_ACCEL_RATE;
        if (yDir > VELOCITY_TO_DELETE) toBeDeleted = true;

        y += yDir;
    }

    @Override
    public ItemSprite getSprite() { return sprites[animationFrame / 2]; }
}
