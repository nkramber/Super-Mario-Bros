package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;

public class PowerUpItem extends Item {

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
    
    public void move() {
        if (inSpawnAnimation && (initialY - y < 16)) y -= 0.5f;
        else if (inSpawnAnimation) inSpawnAnimation = false;
    }

    public boolean inSpawnAnimation() { return inSpawnAnimation; }
    public int getInitialY() { return initialY; }
}
