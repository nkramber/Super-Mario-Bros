package com.nate.mario.gfx.sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerSprite extends Sprite {

    public static final PlayerSprite[] MARIO_SMALL_RUN = {
        new PlayerSprite("mario_small_run1"),
        new PlayerSprite("mario_small_run2"),
        new PlayerSprite("mario_small_run3")
    };
    public static final PlayerSprite MARIO_SMALL_STILL = new PlayerSprite("mario_small_still");
    public static final PlayerSprite MARIO_SMALL_TURN = new PlayerSprite("mario_small_turn");
    public static final PlayerSprite MARIO_SMALL_JUMP = new PlayerSprite("mario_small_jump");
    public static final PlayerSprite MARIO_SMALL_DIE = new PlayerSprite("mario_small_die");
    public static final PlayerSprite MARIO_SMALL_CLIMB1 = new PlayerSprite("mario_small_climb1");
    public static final PlayerSprite MARIO_SMALL_CLIMB2 = new PlayerSprite("mario_small_climb2");
    public static final PlayerSprite MARIO_SMALL_SWIM1 = new PlayerSprite("mario_small_swim1");
    public static final PlayerSprite MARIO_SMALL_SWIM2 = new PlayerSprite("mario_small_swim2");
    public static final PlayerSprite MARIO_SMALL_SWIM3 = new PlayerSprite("mario_small_swim3");
    public static final PlayerSprite MARIO_SMALL_SWIM4 = new PlayerSprite("mario_small_swim4");
    public static final PlayerSprite MARIO_SMALL_SWIM5 = new PlayerSprite("mario_small_swim5");
    public static final List<PlayerSprite> MARIO_SMALL = new ArrayList<>(Arrays.asList(
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

    public static final PlayerSprite[] MARIO_BIG_RUN = {
        new PlayerSprite("mario_big_run1", 2),
        new PlayerSprite("mario_big_run2", 2),
        new PlayerSprite("mario_big_run3", 2)
    };
    public static final PlayerSprite MARIO_BIG_STILL = new PlayerSprite("mario_big_still", 2);
    public static final PlayerSprite MARIO_BIG_TURN = new PlayerSprite("mario_big_turn", 2);
    public static final PlayerSprite MARIO_BIG_JUMP = new PlayerSprite("mario_big_jump", 2);
    public static final PlayerSprite MARIO_BIG_DIE = new PlayerSprite("mario_big_die", 2);
    public static final PlayerSprite MARIO_BIG_CLIMB1 = new PlayerSprite("mario_big_climb1", 2);
    public static final PlayerSprite MARIO_BIG_CLIMB2 = new PlayerSprite("mario_big_climb2", 2);
    public static final PlayerSprite MARIO_BIG_SWIM1 = new PlayerSprite("mario_big_swim1", 2);
    public static final PlayerSprite MARIO_BIG_SWIM2 = new PlayerSprite("mario_big_swim2", 2);
    public static final PlayerSprite MARIO_BIG_SWIM3 = new PlayerSprite("mario_big_swim3", 2);
    public static final PlayerSprite MARIO_BIG_SWIM4 = new PlayerSprite("mario_big_swim4", 2);
    public static final PlayerSprite MARIO_BIG_SWIM5 = new PlayerSprite("mario_big_swim5", 2);
    public static final List<PlayerSprite> MARIO_BIG = new ArrayList<>(Arrays.asList(
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

    public static final PlayerSprite[] MARIO_FIRE_RUN = {
        new PlayerSprite("mario_fire_run1", 2),
        new PlayerSprite("mario_fire_run2", 2),
        new PlayerSprite("mario_fire_run3", 2)
    };
    public static final PlayerSprite MARIO_FIRE_STILL = new PlayerSprite("mario_fire_still", 2);
    public static final PlayerSprite MARIO_FIRE_TURN = new PlayerSprite("mario_fire_turn", 2);
    public static final PlayerSprite MARIO_FIRE_JUMP = new PlayerSprite("mario_fire_jump", 2);
    public static final PlayerSprite MARIO_FIRE_DIE = new PlayerSprite("mario_fire_die", 2);
    public static final PlayerSprite MARIO_FIRE_CLIMB1 = new PlayerSprite("mario_fire_climb1", 2);
    public static final PlayerSprite MARIO_FIRE_CLIMB2 = new PlayerSprite("mario_fire_climb2", 2);
    public static final PlayerSprite MARIO_FIRE_SWIM1 = new PlayerSprite("mario_fire_swim1", 2);
    public static final PlayerSprite MARIO_FIRE_SWIM2 = new PlayerSprite("mario_fire_swim2", 2);
    public static final PlayerSprite MARIO_FIRE_SWIM3 = new PlayerSprite("mario_fire_swim3", 2);
    public static final PlayerSprite MARIO_FIRE_SWIM4 = new PlayerSprite("mario_fire_swim4", 2);
    public static final PlayerSprite MARIO_FIRE_SWIM5 = new PlayerSprite("mario_fire_swim5", 2);
    public static final List<PlayerSprite> MARIO_FIRE = new ArrayList<>(Arrays.asList(
        MARIO_FIRE_RUN[0],
        MARIO_FIRE_RUN[1],
        MARIO_FIRE_RUN[2],
        MARIO_FIRE_STILL,
        MARIO_FIRE_TURN,
        MARIO_FIRE_JUMP,
        MARIO_FIRE_DIE,
        MARIO_FIRE_CLIMB1,
        MARIO_FIRE_CLIMB2,
        MARIO_FIRE_SWIM1,
        MARIO_FIRE_SWIM2,
        MARIO_FIRE_SWIM3,
        MARIO_FIRE_SWIM4,
        MARIO_FIRE_SWIM5
    ));

    public static final PlayerSprite MARIO_FIRE_ANIMATION_GREEN = new PlayerSprite("mario_fire_animation_green", 2);
    public static final PlayerSprite MARIO_FIRE_ANIMATION_RED = new PlayerSprite("mario_fire_animation_red", 2);
    public static final PlayerSprite MARIO_FIRE_ANIMATION_BLACK = new PlayerSprite("mario_fire_animation_black", 2);
    public static final List<PlayerSprite> MARIO_FIRE_ANIMATION = new ArrayList<>(Arrays.asList(
        MARIO_BIG_STILL,
        MARIO_FIRE_STILL,
        MARIO_FIRE_ANIMATION_BLACK,
        MARIO_FIRE_STILL,
        MARIO_FIRE_ANIMATION_GREEN,
        MARIO_FIRE_ANIMATION_RED,
        MARIO_FIRE_ANIMATION_BLACK,
        MARIO_FIRE_STILL,
        MARIO_FIRE_ANIMATION_GREEN,
        MARIO_FIRE_ANIMATION_RED,
        MARIO_FIRE_ANIMATION_BLACK,
        MARIO_FIRE_STILL,
        MARIO_FIRE_ANIMATION_GREEN,
        MARIO_FIRE_ANIMATION_RED,
        MARIO_FIRE_STILL
    ));

    public static final PlayerSprite MARIO_GROW1 = new PlayerSprite("mario_grow1", 2);
    public static final PlayerSprite MARIO_GROW2 = new PlayerSprite("mario_grow2", 2);
    public static final List<PlayerSprite> MARIO_GROW_ANIMATION = new ArrayList<>(Arrays.asList(
        MARIO_GROW1,
        MARIO_GROW2,
        MARIO_GROW1,
        MARIO_GROW2,
        MARIO_GROW1,
        MARIO_GROW2,
        MARIO_GROW1,
        MARIO_GROW2,
        MARIO_BIG_STILL,
        MARIO_BIG_STILL
    ));

    public static final List<PlayerSprite> MARIO_SHRINK_ANIMATION = new ArrayList<>(Arrays.asList(
        MARIO_BIG_STILL,
        MARIO_GROW1,
        MARIO_BIG_STILL,
        MARIO_GROW1,
        MARIO_BIG_STILL,
        MARIO_GROW1,
        MARIO_BIG_STILL,
        MARIO_GROW1,
        MARIO_BIG_STILL,
        MARIO_GROW1
    ));

    public PlayerSprite(String name) {
        super(name, 1);
    }

    public PlayerSprite(String name, int height) {
        super(name, height);
    }
}
