package com.nate.mario.item.powerupitem;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.Item;
import com.nate.mario.level.Level;

public abstract class PowerUpItem extends Item {

    public static final int SCORE = 1000;

    protected boolean inSpawnAnimation = true;
    protected int animationFrame = 0;
    protected int initialY;

    public PowerUpItem(float x, float y) {
        super(x, y); 
        initialY = (int) y;
    }

    @Override
    public void tick(Level level) {
    //Update the animation frame of the item
        animationFrame++;
        if (animationFrame == 8) animationFrame = 0;

        //If we're still spawning, then slowly move the item towards the top of the screen until it clears the block it came from
        if (inSpawnAnimation && (initialY - y < 16)) y -= 0.5f;
        else if (inSpawnAnimation) inSpawnAnimation = false;
    }

    @Override public abstract Item newItem(float x, float y);
    @Override public abstract ItemSprite getSprite();

    public boolean inSpawnAnimation() { return inSpawnAnimation; }
    public int getInitialY() { return initialY; }
}
