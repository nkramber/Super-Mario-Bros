package com.nate.mario.item;

import com.nate.mario.gfx.ItemSprite;

public class AnimatedPowerUpItem extends PowerUpItem {

    protected int animationFrame = 0;

    public AnimatedPowerUpItem(int id, ItemSprite[] sprites) {
        super(id, sprites);
    }

    public AnimatedPowerUpItem(float x, float y, int id, ItemSprite[] sprites) {
        super(x, y, id, sprites);
    }

    public Item newAnimatedItem(float x, float y, int id, ItemSprite[] sprites) {
        return new AnimatedPowerUpItem(x, y, id, sprites);
    }

    public void updateAnimationFrame() {
        animationFrame++;
        if (animationFrame == 8) animationFrame = 0;
    }

    public ItemSprite getSprite() { return sprites[animationFrame / 2]; }
    public ItemSprite[] getSprites() { return sprites; }
}
