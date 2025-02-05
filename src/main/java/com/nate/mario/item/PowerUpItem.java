package com.nate.mario.item;

import com.nate.mario.gfx.ItemSprite;

public class PowerUpItem extends Item {

    protected boolean inSpawnAnimation = true;
    protected int initialY;

    public PowerUpItem(int id, ItemSprite sprite) {
        super(id, sprite);
    }

    public PowerUpItem(int id, ItemSprite[] sprites) {
        super(id, sprites);
    }

    public PowerUpItem(float x, float y, int id, ItemSprite sprite) {
        super(x, y, id, sprite);
        initialY = (int) y;
    }

    public PowerUpItem(float x, float y, int id, ItemSprite[] sprites) {
        super(x, y, id, sprites);
        initialY = (int) y;
    }
    
    public void move() {
        if (inSpawnAnimation && (initialY - y < 16)) y -= 0.5f;
        else if (inSpawnAnimation) inSpawnAnimation = false;
    }

    @Override
    public Item newItem(float x, float y, int id, ItemSprite sprite) {
        return new PowerUpItem(x, y, id, sprite);
    }

    public boolean inSpawnAnimation() { return inSpawnAnimation; }
    public int getInitialY() { return initialY; }
}
