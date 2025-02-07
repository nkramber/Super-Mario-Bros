package com.nate.mario.entity;

import com.nate.mario.gfx.sprite.EnemySprite;

public class Goomba extends Entity {

    private static final float HORIZONTAL_SPEED = -1.0f;

    public Goomba(int id) {
        super(id);
    }

    public Goomba(float xTile, float yTile) {
        super(xTile, yTile, HORIZONTAL_SPEED, 0, EnemySprite.GOOMBA1);
    }

    public void move() {
        x += xDir;
        y += yDir;
    }

    @Override
    public Entity newEntity(int xTile, int yTile) {
        return new Goomba(xTile, yTile);
    }
}
