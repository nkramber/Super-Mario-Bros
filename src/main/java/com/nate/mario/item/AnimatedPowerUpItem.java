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
    
    public void updateAnimationFrame() {
        animationFrame++;
        if (animationFrame == 8) animationFrame = 0;
    }

    public ItemSprite getSprite() { return sprites[animationFrame / 2]; }
    public ItemSprite[] getSprites() { return sprites; }
}
