package com.nate.mario.item.powerupitem;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.Item;

public abstract class AnimatedPowerUpItem extends PowerUpItem {

    protected int animationFrame = 0;

    public AnimatedPowerUpItem(float x, float y) {
        super(x, y);
    }

    @Override
    public void tick() {
        //Update the animation frame of the item
        animationFrame++;
        if (animationFrame == 8) animationFrame = 0;

        //If we're still spawning, then slowly move the item towards the top of the screen until it clears the block it came from
        if (inSpawnAnimation && (initialY - y < 16)) y -= 0.5f;
        else if (inSpawnAnimation) inSpawnAnimation = false;
    }

    @Override public abstract ItemSprite getSprite();
    
    @Override public Item newItem(float x, float y) { return null; }
    @Override public int getID() { return -1; }
}
