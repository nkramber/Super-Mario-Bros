package com.nate.mario.item;

import com.nate.mario.entity.player.Player;
import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.level.Level;

public class BlockCoinItem extends Item {

    public static final int ID = 1;
    private static final ItemSprite[] SPRITES = ItemSprite.ANIMATED_COIN;

    private static float VER_ACCEL_RATE = Player.VER_ACCEL_RATE;
    private static float VELOCITY_TO_DELETE = 4.5f;

    private int animationFrame = 0;
    private float yDir = -5.5f;

    public BlockCoinItem(float x, float y) {
        super(x, y);
    }

    @Override
    public void tick(Level level) {
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

    @Override public ItemSprite getSprite() { return SPRITES[animationFrame / 2]; }
    @Override public Item newItem(float x, float y) { return new BlockCoinItem(x, y); }
}
