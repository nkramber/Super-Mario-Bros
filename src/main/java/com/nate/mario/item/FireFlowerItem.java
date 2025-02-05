package com.nate.mario.item;

import com.nate.mario.gfx.ItemSprite;

public class FireFlowerItem extends AnimatedPowerUpItem {

    protected boolean inSpawnAnimation = true;
    protected int initialY;

    public FireFlowerItem(int id, ItemSprite[] sprites) {
        super(id, sprites);
    }

    public FireFlowerItem(float x, float y, int id, ItemSprite[] sprites) {
        super(x, y, id, sprites);
    }

    @Override
    public Item newAnimatedItem(float x, float y, int id, ItemSprite[] sprites) {
        return new FireFlowerItem(x, y, id, sprites);
    }
}
