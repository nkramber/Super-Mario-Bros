package com.nate.mario.gfx.sprite;

public class EnemySprite extends Sprite {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    public static final EnemySprite GOOMBA1 = new EnemySprite("goomba1");
    public static final EnemySprite GOOMBA2 = new EnemySprite("goomba2");
    public static final EnemySprite GOOMBA_DEAD = new EnemySprite("goomba_dead");


    public EnemySprite(String name) {
        super(name, 1);
    }

    public EnemySprite(String name, int height) {
        super(name, height);
    }
}
