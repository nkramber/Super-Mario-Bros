package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;

public class AnimatedPowerUpItem extends PowerUpItem {

    protected int animationFrame = 0;

    public AnimatedPowerUpItem(int id) {
        super(id);
    }

    public AnimatedPowerUpItem(float x, float y, ItemSprite[] sprites) {
        super(x, y, sprites);
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

    public ItemSprite getSprite() { return sprites[animationFrame / 2]; }
    public ItemSprite[] getSprites() { return sprites; }
}
