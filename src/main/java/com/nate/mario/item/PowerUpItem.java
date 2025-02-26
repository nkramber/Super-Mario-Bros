package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;

public class PowerUpItem extends Item {

    public static final int SCORE = 1000;

    protected boolean inSpawnAnimation = true;
    protected int initialY;

    public PowerUpItem(int id) {
        super(id);
    }

    public PowerUpItem(float x, float y, ItemSprite sprite) {
        super(x, y, sprite);
        initialY = (int) y;
    }

    public PowerUpItem(float x, float y, ItemSprite[] sprites) {
        super(x, y, sprites);
        initialY = (int) y;
    }

    public boolean inSpawnAnimation() { return inSpawnAnimation; }
    public int getInitialY() { return initialY; }
}
