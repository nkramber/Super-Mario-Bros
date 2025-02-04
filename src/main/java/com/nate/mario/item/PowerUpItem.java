package com.nate.mario.item;

public class PowerUpItem extends Item {

    protected boolean inSpawnAnimation = true;
    protected int initialY;

    public PowerUpItem(int id, String name) {
        super(id, name);
    }

    public PowerUpItem(float x, float y, int id, String name) {
        super(x, y, id, name);
        initialY = (int) y;
    }
    
    @Override
    public Item newItem(float x, float y, int id, String name) {
        return new PowerUpItem(x, y, id, name);
    }

    public boolean inSpawnAnimation() { return inSpawnAnimation; }
    public int getInitialY() { return initialY; }
}
