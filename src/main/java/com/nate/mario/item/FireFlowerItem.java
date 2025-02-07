package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;

public class FireFlowerItem extends AnimatedPowerUpItem {

    protected boolean inSpawnAnimation = true;
    protected int initialY;

    public FireFlowerItem(int id) {
        super(id);
    }

    public FireFlowerItem(float x, float y) {
        super(x, y, ItemSprite.FIRE_FLOWER);
    }

    @Override
    public Item newItem(float x, float y) {
        return new FireFlowerItem(x, y);
    }
}
