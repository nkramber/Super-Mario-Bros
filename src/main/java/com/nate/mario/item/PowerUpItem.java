package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;

public abstract class PowerUpItem extends Item {

    public static final int SCORE = 1000;

    protected boolean inSpawnAnimation = true;
    protected int initialY;

    public PowerUpItem(float x, float y) {
        super(x, y); 
        initialY = (int) y;
    }

    @Override public abstract Item newItem(float x, float y);
    @Override public abstract int getID();
    @Override public abstract ItemSprite getSprite();

    public boolean inSpawnAnimation() { return inSpawnAnimation; }
    public int getInitialY() { return initialY; }
}
