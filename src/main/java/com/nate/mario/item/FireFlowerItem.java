package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;

public class FireFlowerItem extends AnimatedPowerUpItem {

    private static final ItemSprite[] SPRITES = ItemSprite.FIRE_FLOWER;

    public FireFlowerItem(float x, float y) {
        super(x, y);
    }

    @Override public ItemSprite getSprite() { return SPRITES[animationFrame / 2]; }
}
