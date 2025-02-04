package com.nate.mario.gfx;

public class EntitySprite {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    public static final EntitySprite MARIO_SMALL_STILL = new EntitySprite("mario_small_still");
    public static final EntitySprite MARIO_SMALL_RUN1 = new EntitySprite("mario_small_run1");
    public static final EntitySprite MARIO_SMALL_RUN2 = new EntitySprite("mario_small_run2");
    public static final EntitySprite MARIO_SMALL_RUN3 = new EntitySprite("mario_small_run3");
    public static final EntitySprite MARIO_SMALL_TURN = new EntitySprite("mario_small_turn");
    public static final EntitySprite MARIO_SMALL_JUMP = new EntitySprite("mario_small_jump");
    public static final EntitySprite MARIO_SMALL_DIE = new EntitySprite("mario_small_die");
    public static final EntitySprite MARIO_SMALL_CLIMB1 = new EntitySprite("mario_small_climb1");
    public static final EntitySprite MARIO_SMALL_CLIMB2 = new EntitySprite("mario_small_climb2");
    public static final EntitySprite MARIO_SMALL_SWIM1 = new EntitySprite("mario_small_swim1");
    public static final EntitySprite MARIO_SMALL_SWIM2 = new EntitySprite("mario_small_swim2");
    public static final EntitySprite MARIO_SMALL_SWIM3 = new EntitySprite("mario_small_swim3");
    public static final EntitySprite MARIO_SMALL_SWIM4 = new EntitySprite("mario_small_swim4");
    public static final EntitySprite MARIO_SMALL_SWIM5 = new EntitySprite("mario_small_swim5");
    public static final EntitySprite MARIO_TALL_STILL = new EntitySprite("mario_tall_still");

    private String name;
    private int height;

    public EntitySprite(String name) {
        this.name = name;
        this.height = 1;
    }

    public EntitySprite(String name, int height) {
        this.name = name;
        this.height = height;
    }

    public String getName() { return name; }
    public int getHeight() { return height; }
}
