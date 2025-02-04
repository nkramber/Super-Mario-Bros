package com.nate.mario.gfx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntitySprite {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    public static final EntitySprite[] MARIO_SMALL_RUN = {
        new EntitySprite("mario_small_run1"),
        new EntitySprite("mario_small_run2"),
        new EntitySprite("mario_small_run3")
    };

    public static final EntitySprite MARIO_SMALL_STILL = new EntitySprite("mario_small_still");
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

    public static final List<EntitySprite> MARIO_SMALL = new ArrayList<>(Arrays.asList(
        MARIO_SMALL_RUN[0],
        MARIO_SMALL_RUN[1],
        MARIO_SMALL_RUN[2],
        MARIO_SMALL_STILL,
        MARIO_SMALL_TURN,
        MARIO_SMALL_JUMP,
        MARIO_SMALL_DIE,
        MARIO_SMALL_CLIMB1,
        MARIO_SMALL_CLIMB2,
        MARIO_SMALL_SWIM1,
        MARIO_SMALL_SWIM2,
        MARIO_SMALL_SWIM3,
        MARIO_SMALL_SWIM4,
        MARIO_SMALL_SWIM5
    ));

    public static final EntitySprite[] MARIO_BIG_RUN = {
        new EntitySprite("mario_big_run1", 2),
        new EntitySprite("mario_big_run2", 2),
        new EntitySprite("mario_big_run3", 2)
    };

    public static final EntitySprite MARIO_BIG_STILL = new EntitySprite("mario_big_still", 2);
    public static final EntitySprite MARIO_BIG_TURN = new EntitySprite("mario_big_turn", 2);
    public static final EntitySprite MARIO_BIG_JUMP = new EntitySprite("mario_big_jump", 2);
    public static final EntitySprite MARIO_BIG_DIE = new EntitySprite("mario_big_die", 2);
    public static final EntitySprite MARIO_BIG_CLIMB1 = new EntitySprite("mario_big_climb1", 2);
    public static final EntitySprite MARIO_BIG_CLIMB2 = new EntitySprite("mario_big_climb2", 2);
    public static final EntitySprite MARIO_BIG_SWIM1 = new EntitySprite("mario_big_swim1", 2);
    public static final EntitySprite MARIO_BIG_SWIM2 = new EntitySprite("mario_big_swim2", 2);
    public static final EntitySprite MARIO_BIG_SWIM3 = new EntitySprite("mario_big_swim3", 2);
    public static final EntitySprite MARIO_BIG_SWIM4 = new EntitySprite("mario_big_swim4", 2);
    public static final EntitySprite MARIO_BIG_SWIM5 = new EntitySprite("mario_big_swim5", 2);

    public static final List<EntitySprite> MARIO_BIG = new ArrayList<>(Arrays.asList(
        MARIO_BIG_RUN[0],
        MARIO_BIG_RUN[1],
        MARIO_BIG_RUN[2],
        MARIO_BIG_STILL,
        MARIO_BIG_TURN,
        MARIO_BIG_JUMP,
        MARIO_BIG_DIE,
        MARIO_BIG_CLIMB1,
        MARIO_BIG_CLIMB2,
        MARIO_BIG_SWIM1,
        MARIO_BIG_SWIM2,
        MARIO_BIG_SWIM3,
        MARIO_BIG_SWIM4,
        MARIO_BIG_SWIM5
    ));

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
