package com.nate.mario.item;

import com.nate.mario.entity.player.Player;
import com.nate.mario.gfx.sprite.ItemSprite;

public class BlockCoin extends Item {

    private static float VER_ACCEL_RATE = Player.VER_ACCEL_RATE;
    private static float VELOCITY_TO_DELETE = 4.5f;

    private int animationFrame = 0;
    private float yDir = -5.5f;

    public BlockCoin(int id) {
        super(id);
    }

    public BlockCoin(float x, float y) {
        super(x, y, ItemSprite.ANIMATED_COIN);
    }

    @Override
    public void tick() {
        //Update the animation frame of the coin
        animationFrame++;
        if (animationFrame == 8) animationFrame = 0;

        //Add gravity to the coin
        yDir += VER_ACCEL_RATE;
        //If we're going faster than this, it means we've reached the point where we should be deleted
        if (yDir > VELOCITY_TO_DELETE) toBeDeleted = true;

        //Add our y momentum to our y coordinate
        y += yDir;
    }

    @Override
    public ItemSprite getSprite() { return sprites[animationFrame / 2]; }
}
