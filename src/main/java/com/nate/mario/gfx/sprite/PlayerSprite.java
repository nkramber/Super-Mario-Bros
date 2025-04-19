package com.nate.mario.gfx.sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nate.mario.entity.player.PowerUpState;

public class PlayerSprite extends Sprite {

    public final int index;

    public static final PlayerSprite MARIO_SMALL_STILL = new PlayerSprite("mario_small_still", 0);
    public static final PlayerSprite[] MARIO_SMALL_RUN = {
        new PlayerSprite("mario_small_run1", 1),
        new PlayerSprite("mario_small_run2", 2),
        new PlayerSprite("mario_small_run3", 3)
    };
    public static final PlayerSprite MARIO_SMALL_TURN = new PlayerSprite("mario_small_turn", 4);
    public static final PlayerSprite MARIO_SMALL_JUMP = new PlayerSprite("mario_small_jump", 5);
    public static final PlayerSprite MARIO_SMALL_DIE = new PlayerSprite("mario_small_die", 6);
    public static final PlayerSprite MARIO_SMALL_CLIMB1 = new PlayerSprite("mario_small_climb1", 7);
    public static final PlayerSprite MARIO_SMALL_CLIMB2 = new PlayerSprite("mario_small_climb2", 8);
    public static final PlayerSprite MARIO_SMALL_SWIM1 = new PlayerSprite("mario_small_swim1", 9);
    public static final PlayerSprite MARIO_SMALL_SWIM2 = new PlayerSprite("mario_small_swim2", 10);
    public static final PlayerSprite MARIO_SMALL_SWIM3 = new PlayerSprite("mario_small_swim3", 11);
    public static final PlayerSprite MARIO_SMALL_SWIM4 = new PlayerSprite("mario_small_swim4", 12);
    public static final PlayerSprite MARIO_SMALL_SWIM5 = new PlayerSprite("mario_small_swim5", 13);
    public static final List<PlayerSprite> MARIO_SMALL = new ArrayList<>(Arrays.asList(
        MARIO_SMALL_STILL,
        MARIO_SMALL_RUN[0],
        MARIO_SMALL_RUN[1],
        MARIO_SMALL_RUN[2],
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

    public static final PlayerSprite MARIO_BIG_STILL = new PlayerSprite("mario_big_still", 0, 2);
    public static final PlayerSprite[] MARIO_BIG_RUN = {
        new PlayerSprite("mario_big_run1", 1, 2),
        new PlayerSprite("mario_big_run2", 2, 2),
        new PlayerSprite("mario_big_run3", 3, 2)
    };
    public static final PlayerSprite MARIO_BIG_TURN = new PlayerSprite("mario_big_turn", 4, 2);
    public static final PlayerSprite MARIO_BIG_JUMP = new PlayerSprite("mario_big_jump", 5, 2);
    public static final PlayerSprite MARIO_BIG_CROUCH = new PlayerSprite("mario_big_crouch", 6, 2);
    public static final PlayerSprite MARIO_BIG_CLIMB1 = new PlayerSprite("mario_big_climb1", 7, 2);
    public static final PlayerSprite MARIO_BIG_CLIMB2 = new PlayerSprite("mario_big_climb2", 8, 2);
    public static final PlayerSprite MARIO_BIG_SWIM1 = new PlayerSprite("mario_big_swim1", 9, 2);
    public static final PlayerSprite MARIO_BIG_SWIM2 = new PlayerSprite("mario_big_swim2", 10, 2);
    public static final PlayerSprite MARIO_BIG_SWIM3 = new PlayerSprite("mario_big_swim3", 11, 2);
    public static final PlayerSprite MARIO_BIG_SWIM4 = new PlayerSprite("mario_big_swim4", 12, 2);
    public static final PlayerSprite MARIO_BIG_SWIM5 = new PlayerSprite("mario_big_swim5", 13, 2);
    public static final List<PlayerSprite> MARIO_BIG = new ArrayList<>(Arrays.asList(
        MARIO_BIG_STILL,
        MARIO_BIG_RUN[0],
        MARIO_BIG_RUN[1],
        MARIO_BIG_RUN[2],
        MARIO_BIG_TURN,
        MARIO_BIG_JUMP,
        MARIO_BIG_CROUCH,
        MARIO_BIG_CLIMB1,
        MARIO_BIG_CLIMB2,
        MARIO_BIG_SWIM1,
        MARIO_BIG_SWIM2,
        MARIO_BIG_SWIM3,
        MARIO_BIG_SWIM4,
        MARIO_BIG_SWIM5
    ));

    public static final PlayerSprite MARIO_FIRE_STILL = new PlayerSprite("mario_fire_still", 0, 2);
    public static final PlayerSprite[] MARIO_FIRE_RUN = {
        new PlayerSprite("mario_fire_run1", 1, 2),
        new PlayerSprite("mario_fire_run2", 2, 2),
        new PlayerSprite("mario_fire_run3", 3, 2)
    };
    public static final PlayerSprite MARIO_FIRE_TURN = new PlayerSprite("mario_fire_turn", 4, 2);
    public static final PlayerSprite MARIO_FIRE_JUMP = new PlayerSprite("mario_fire_jump", 5, 2);
    public static final PlayerSprite MARIO_FIRE_CROUCH = new PlayerSprite("mario_fire_crouch", 6, 2);
    public static final PlayerSprite MARIO_FIRE_CLIMB1 = new PlayerSprite("mario_fire_climb1", 7, 2);
    public static final PlayerSprite MARIO_FIRE_CLIMB2 = new PlayerSprite("mario_fire_climb2", 8, 2);
    public static final PlayerSprite MARIO_FIRE_SWIM1 = new PlayerSprite("mario_fire_swim1", 9, 2);
    public static final PlayerSprite MARIO_FIRE_SWIM2 = new PlayerSprite("mario_fire_swim2", 10, 2);
    public static final PlayerSprite MARIO_FIRE_SWIM3 = new PlayerSprite("mario_fire_swim3", 11, 2);
    public static final PlayerSprite MARIO_FIRE_SWIM4 = new PlayerSprite("mario_fire_swim4", 12, 2);
    public static final PlayerSprite MARIO_FIRE_SWIM5 = new PlayerSprite("mario_fire_swim5", 13, 2);
    public static final List<PlayerSprite> MARIO_FIRE = new ArrayList<>(Arrays.asList(
        MARIO_FIRE_STILL,
        MARIO_FIRE_RUN[0],
        MARIO_FIRE_RUN[1],
        MARIO_FIRE_RUN[2],
        MARIO_FIRE_TURN,
        MARIO_FIRE_JUMP,
        MARIO_FIRE_CROUCH,
        MARIO_FIRE_CLIMB1,
        MARIO_FIRE_CLIMB2,
        MARIO_FIRE_SWIM1,
        MARIO_FIRE_SWIM2,
        MARIO_FIRE_SWIM3,
        MARIO_FIRE_SWIM4,
        MARIO_FIRE_SWIM5
    ));

    public static final PlayerSprite MARIO_SMALL_GREEN_STILL = new PlayerSprite("mario_small_green_still", 0);
    public static final PlayerSprite[] MARIO_SMALL_GREEN_RUN = {
        new PlayerSprite("mario_small_green_run1", 1),
        new PlayerSprite("mario_small_green_run2", 2),
        new PlayerSprite("mario_small_green_run3", 3)
    };
    public static final PlayerSprite MARIO_SMALL_GREEN_TURN = new PlayerSprite("mario_small_green_turn", 4);
    public static final PlayerSprite MARIO_SMALL_GREEN_JUMP = new PlayerSprite("mario_small_green_jump", 5);
    public static final PlayerSprite MARIO_SMALL_GREEN_DIE = new PlayerSprite("mario_small_green_die", 6);
    public static final PlayerSprite MARIO_SMALL_GREEN_CLIMB1 = new PlayerSprite("mario_small_green_climb1", 7);
    public static final PlayerSprite MARIO_SMALL_GREEN_CLIMB2 = new PlayerSprite("mario_small_green_climb2", 8);
    public static final PlayerSprite MARIO_SMALL_GREEN_SWIM1 = new PlayerSprite("mario_small_green_swim1", 9);
    public static final PlayerSprite MARIO_SMALL_GREEN_SWIM2 = new PlayerSprite("mario_small_green_swim2", 10);
    public static final PlayerSprite MARIO_SMALL_GREEN_SWIM3 = new PlayerSprite("mario_small_green_swim3", 11);
    public static final PlayerSprite MARIO_SMALL_GREEN_SWIM4 = new PlayerSprite("mario_small_green_swim4", 12);
    public static final PlayerSprite MARIO_SMALL_GREEN_SWIM5 = new PlayerSprite("mario_small_green_swim5", 13);
    public static final List<PlayerSprite> MARIO_SMALL_GREEN = new ArrayList<>(Arrays.asList(
        MARIO_SMALL_GREEN_STILL,
        MARIO_SMALL_GREEN_RUN[0],
        MARIO_SMALL_GREEN_RUN[1],
        MARIO_SMALL_GREEN_RUN[2],
        MARIO_SMALL_GREEN_TURN,
        MARIO_SMALL_GREEN_JUMP,
        MARIO_SMALL_GREEN_DIE,
        MARIO_SMALL_GREEN_CLIMB1,
        MARIO_SMALL_GREEN_CLIMB2,
        MARIO_SMALL_GREEN_SWIM1,
        MARIO_SMALL_GREEN_SWIM2,
        MARIO_SMALL_GREEN_SWIM3,
        MARIO_SMALL_GREEN_SWIM4,
        MARIO_SMALL_GREEN_SWIM5
    ));

    public static final PlayerSprite MARIO_SMALL_RED_STILL = new PlayerSprite("mario_small_red_still", 0);
    public static final PlayerSprite[] MARIO_SMALL_RED_RUN = {
        new PlayerSprite("mario_small_red_run1", 1),
        new PlayerSprite("mario_small_red_run2", 2),
        new PlayerSprite("mario_small_red_run3", 3)
    };
    public static final PlayerSprite MARIO_SMALL_RED_TURN = new PlayerSprite("mario_small_red_turn", 4);
    public static final PlayerSprite MARIO_SMALL_RED_JUMP = new PlayerSprite("mario_small_red_jump", 5);
    public static final PlayerSprite MARIO_SMALL_RED_DIE = new PlayerSprite("mario_small_red_die", 6);
    public static final PlayerSprite MARIO_SMALL_RED_CLIMB1 = new PlayerSprite("mario_small_red_climb1", 7);
    public static final PlayerSprite MARIO_SMALL_RED_CLIMB2 = new PlayerSprite("mario_small_red_climb2", 8);
    public static final PlayerSprite MARIO_SMALL_RED_SWIM1 = new PlayerSprite("mario_small_red_swim1", 9);
    public static final PlayerSprite MARIO_SMALL_RED_SWIM2 = new PlayerSprite("mario_small_red_swim2", 10);
    public static final PlayerSprite MARIO_SMALL_RED_SWIM3 = new PlayerSprite("mario_small_red_swim3", 11);
    public static final PlayerSprite MARIO_SMALL_RED_SWIM4 = new PlayerSprite("mario_small_red_swim4", 12);
    public static final PlayerSprite MARIO_SMALL_RED_SWIM5 = new PlayerSprite("mario_small_red_swim5", 13);
    public static final List<PlayerSprite> MARIO_SMALL_RED = new ArrayList<>(Arrays.asList(
        MARIO_SMALL_RED_STILL,
        MARIO_SMALL_RED_RUN[0],
        MARIO_SMALL_RED_RUN[1],
        MARIO_SMALL_RED_RUN[2],
        MARIO_SMALL_RED_TURN,
        MARIO_SMALL_RED_JUMP,
        MARIO_SMALL_RED_DIE,
        MARIO_SMALL_RED_CLIMB1,
        MARIO_SMALL_RED_CLIMB2,
        MARIO_SMALL_RED_SWIM1,
        MARIO_SMALL_RED_SWIM2,
        MARIO_SMALL_RED_SWIM3,
        MARIO_SMALL_RED_SWIM4,
        MARIO_SMALL_RED_SWIM5
    ));

    public static final PlayerSprite MARIO_SMALL_BLACK_STILL = new PlayerSprite("mario_small_black_still", 0);
    public static final PlayerSprite[] MARIO_SMALL_BLACK_RUN = {
        new PlayerSprite("mario_small_black_run1", 1),
        new PlayerSprite("mario_small_black_run2", 2),
        new PlayerSprite("mario_small_black_run3", 3)
    };
    public static final PlayerSprite MARIO_SMALL_BLACK_TURN = new PlayerSprite("mario_small_black_turn", 4);
    public static final PlayerSprite MARIO_SMALL_BLACK_JUMP = new PlayerSprite("mario_small_black_jump", 5);
    public static final PlayerSprite MARIO_SMALL_BLACK_DIE = new PlayerSprite("mario_small_black_die", 6);
    public static final PlayerSprite MARIO_SMALL_BLACK_CLIMB1 = new PlayerSprite("mario_small_black_climb1", 7);
    public static final PlayerSprite MARIO_SMALL_BLACK_CLIMB2 = new PlayerSprite("mario_small_black_climb2", 8);
    public static final PlayerSprite MARIO_SMALL_BLACK_SWIM1 = new PlayerSprite("mario_small_black_swim1", 9);
    public static final PlayerSprite MARIO_SMALL_BLACK_SWIM2 = new PlayerSprite("mario_small_black_swim2", 10);
    public static final PlayerSprite MARIO_SMALL_BLACK_SWIM3 = new PlayerSprite("mario_small_black_swim3", 11);
    public static final PlayerSprite MARIO_SMALL_BLACK_SWIM4 = new PlayerSprite("mario_small_black_swim4", 12);
    public static final PlayerSprite MARIO_SMALL_BLACK_SWIM5 = new PlayerSprite("mario_small_black_swim5", 13);
    public static final List<PlayerSprite> MARIO_SMALL_BLACK = new ArrayList<>(Arrays.asList(
        MARIO_SMALL_BLACK_STILL,
        MARIO_SMALL_BLACK_RUN[0],
        MARIO_SMALL_BLACK_RUN[1],
        MARIO_SMALL_BLACK_RUN[2],
        MARIO_SMALL_BLACK_TURN,
        MARIO_SMALL_BLACK_JUMP,
        MARIO_SMALL_BLACK_DIE,
        MARIO_SMALL_BLACK_CLIMB1,
        MARIO_SMALL_BLACK_CLIMB2,
        MARIO_SMALL_BLACK_SWIM1,
        MARIO_SMALL_BLACK_SWIM2,
        MARIO_SMALL_BLACK_SWIM3,
        MARIO_SMALL_BLACK_SWIM4,
        MARIO_SMALL_BLACK_SWIM5
    ));

    public static final PlayerSprite MARIO_BIG_GREEN_STILL = new PlayerSprite("mario_big_green_still", 0);
    public static final PlayerSprite[] MARIO_BIG_GREEN_RUN = {
        new PlayerSprite("mario_big_green_run1", 1),
        new PlayerSprite("mario_big_green_run2", 2),
        new PlayerSprite("mario_big_green_run3", 3)
    };
    public static final PlayerSprite MARIO_BIG_GREEN_TURN = new PlayerSprite("mario_big_green_turn", 4);
    public static final PlayerSprite MARIO_BIG_GREEN_JUMP = new PlayerSprite("mario_big_green_jump", 5);
    public static final PlayerSprite MARIO_BIG_GREEN_CROUCH = new PlayerSprite("mario_big_green_crouch", 6);
    public static final PlayerSprite MARIO_BIG_GREEN_CLIMB1 = new PlayerSprite("mario_big_green_climb1", 7);
    public static final PlayerSprite MARIO_BIG_GREEN_CLIMB2 = new PlayerSprite("mario_big_green_climb2", 8);
    public static final PlayerSprite MARIO_BIG_GREEN_SWIM1 = new PlayerSprite("mario_big_green_swim1", 9);
    public static final PlayerSprite MARIO_BIG_GREEN_SWIM2 = new PlayerSprite("mario_big_green_swim2", 10);
    public static final PlayerSprite MARIO_BIG_GREEN_SWIM3 = new PlayerSprite("mario_big_green_swim3", 11);
    public static final PlayerSprite MARIO_BIG_GREEN_SWIM4 = new PlayerSprite("mario_big_green_swim4", 12);
    public static final PlayerSprite MARIO_BIG_GREEN_SWIM5 = new PlayerSprite("mario_big_green_swim5", 13);
    public static final List<PlayerSprite> MARIO_BIG_GREEN = new ArrayList<>(Arrays.asList(
        MARIO_BIG_GREEN_STILL,
        MARIO_BIG_GREEN_RUN[0],
        MARIO_BIG_GREEN_RUN[1],
        MARIO_BIG_GREEN_RUN[2],
        MARIO_BIG_GREEN_TURN,
        MARIO_BIG_GREEN_JUMP,
        MARIO_BIG_GREEN_CROUCH,
        MARIO_BIG_GREEN_CLIMB1,
        MARIO_BIG_GREEN_CLIMB2,
        MARIO_BIG_GREEN_SWIM1,
        MARIO_BIG_GREEN_SWIM2,
        MARIO_BIG_GREEN_SWIM3,
        MARIO_BIG_GREEN_SWIM4,
        MARIO_BIG_GREEN_SWIM5
    ));

    public static final PlayerSprite MARIO_BIG_RED_STILL = new PlayerSprite("mario_big_red_still", 0);
    public static final PlayerSprite[] MARIO_BIG_RED_RUN = {
        new PlayerSprite("mario_big_red_run1", 1),
        new PlayerSprite("mario_big_red_run2", 2),
        new PlayerSprite("mario_big_red_run3", 3)
    };
    public static final PlayerSprite MARIO_BIG_RED_TURN = new PlayerSprite("mario_big_red_turn", 4);
    public static final PlayerSprite MARIO_BIG_RED_JUMP = new PlayerSprite("mario_big_red_jump", 5);
    public static final PlayerSprite MARIO_BIG_RED_CROUCH = new PlayerSprite("mario_big_red_crouch", 6);
    public static final PlayerSprite MARIO_BIG_RED_CLIMB1 = new PlayerSprite("mario_big_red_climb1", 7);
    public static final PlayerSprite MARIO_BIG_RED_CLIMB2 = new PlayerSprite("mario_big_red_climb2", 8);
    public static final PlayerSprite MARIO_BIG_RED_SWIM1 = new PlayerSprite("mario_big_red_swim1", 9);
    public static final PlayerSprite MARIO_BIG_RED_SWIM2 = new PlayerSprite("mario_big_red_swim2", 10);
    public static final PlayerSprite MARIO_BIG_RED_SWIM3 = new PlayerSprite("mario_big_red_swim3", 11);
    public static final PlayerSprite MARIO_BIG_RED_SWIM4 = new PlayerSprite("mario_big_red_swim4", 12);
    public static final PlayerSprite MARIO_BIG_RED_SWIM5 = new PlayerSprite("mario_big_red_swim5", 13);
    public static final List<PlayerSprite> MARIO_BIG_RED = new ArrayList<>(Arrays.asList(
        MARIO_BIG_RED_STILL,
        MARIO_BIG_RED_RUN[0],
        MARIO_BIG_RED_RUN[1],
        MARIO_BIG_RED_RUN[2],
        MARIO_BIG_RED_TURN,
        MARIO_BIG_RED_JUMP,
        MARIO_BIG_RED_CROUCH,
        MARIO_BIG_RED_CLIMB1,
        MARIO_BIG_RED_CLIMB2,
        MARIO_BIG_RED_SWIM1,
        MARIO_BIG_RED_SWIM2,
        MARIO_BIG_RED_SWIM3,
        MARIO_BIG_RED_SWIM4,
        MARIO_BIG_RED_SWIM5
    ));

    public static final PlayerSprite MARIO_BIG_BLACK_STILL = new PlayerSprite("mario_big_black_still", 0);
    public static final PlayerSprite[] MARIO_BIG_BLACK_RUN = {
        new PlayerSprite("mario_big_black_run1", 1),
        new PlayerSprite("mario_big_black_run2", 2),
        new PlayerSprite("mario_big_black_run3", 3)
    };
    public static final PlayerSprite MARIO_BIG_BLACK_TURN = new PlayerSprite("mario_big_black_turn", 4);
    public static final PlayerSprite MARIO_BIG_BLACK_JUMP = new PlayerSprite("mario_big_black_jump", 5);
    public static final PlayerSprite MARIO_BIG_BLACK_CROUCH = new PlayerSprite("mario_big_black_crouch", 6);
    public static final PlayerSprite MARIO_BIG_BLACK_CLIMB1 = new PlayerSprite("mario_big_black_climb1", 7);
    public static final PlayerSprite MARIO_BIG_BLACK_CLIMB2 = new PlayerSprite("mario_big_black_climb2", 8);
    public static final PlayerSprite MARIO_BIG_BLACK_SWIM1 = new PlayerSprite("mario_big_black_swim1", 9);
    public static final PlayerSprite MARIO_BIG_BLACK_SWIM2 = new PlayerSprite("mario_big_black_swim2", 10);
    public static final PlayerSprite MARIO_BIG_BLACK_SWIM3 = new PlayerSprite("mario_big_black_swim3", 11);
    public static final PlayerSprite MARIO_BIG_BLACK_SWIM4 = new PlayerSprite("mario_big_black_swim4", 12);
    public static final PlayerSprite MARIO_BIG_BLACK_SWIM5 = new PlayerSprite("mario_big_black_swim5", 13);
    public static final List<PlayerSprite> MARIO_BIG_BLACK = new ArrayList<>(Arrays.asList(
        MARIO_BIG_BLACK_STILL,
        MARIO_BIG_BLACK_RUN[0],
        MARIO_BIG_BLACK_RUN[1],
        MARIO_BIG_BLACK_RUN[2],
        MARIO_BIG_BLACK_TURN,
        MARIO_BIG_BLACK_JUMP,
        MARIO_BIG_BLACK_CROUCH,
        MARIO_BIG_BLACK_CLIMB1,
        MARIO_BIG_BLACK_CLIMB2,
        MARIO_BIG_BLACK_SWIM1,
        MARIO_BIG_BLACK_SWIM2,
        MARIO_BIG_BLACK_SWIM3,
        MARIO_BIG_BLACK_SWIM4,
        MARIO_BIG_BLACK_SWIM5
    ));

    public static final List<PlayerSprite> MARIO_FIRE_ANIMATION = new ArrayList<>(Arrays.asList(
        MARIO_BIG_STILL,
        MARIO_FIRE_STILL,
        MARIO_BIG_BLACK_STILL,
        MARIO_FIRE_STILL,
        MARIO_BIG_GREEN_STILL,
        MARIO_BIG_RED_STILL,
        MARIO_BIG_BLACK_STILL,
        MARIO_FIRE_STILL,
        MARIO_BIG_GREEN_STILL,
        MARIO_BIG_RED_STILL,
        MARIO_FIRE_STILL,
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

    private static final List<List<PlayerSprite>> SMALL_STAR_SPRITES = new ArrayList<>(Arrays.asList(
        MARIO_SMALL,
        MARIO_SMALL_GREEN,
        MARIO_SMALL_RED,
        MARIO_SMALL_BLACK
    ));

    private static final List<List<PlayerSprite>> BIG_STAR_SPRITES = new ArrayList<>(Arrays.asList(
        MARIO_BIG,
        MARIO_BIG_GREEN,
        MARIO_BIG_RED,
        MARIO_BIG_BLACK
    ));

    private static final List<List<PlayerSprite>> FIRE_STAR_SPRITES = new ArrayList<>(Arrays.asList(
        MARIO_FIRE,
        MARIO_BIG_GREEN,
        MARIO_BIG_RED,
        MARIO_BIG_BLACK 
    ));

    private static final Map<PowerUpState, PlayerSprite[]> runSprites = new HashMap<>() {{
        put(PowerUpState.SMALL, MARIO_SMALL_RUN);
        put(PowerUpState.BIG, MARIO_BIG_RUN);
        put(PowerUpState.FIRE, MARIO_FIRE_RUN);
    }};

    private static final Map<PowerUpState, List<List<PlayerSprite>>> starSpriteCollection = new HashMap<>() {{
        put(PowerUpState.SMALL, SMALL_STAR_SPRITES);
        put(PowerUpState.BIG, BIG_STAR_SPRITES);
        put(PowerUpState.FIRE, FIRE_STAR_SPRITES);
    }};

    public PlayerSprite(String name, int index) {
        super(name, 1);
        this.index = index;
    }

    public PlayerSprite(String name, int index, int height) {
        super(name, height);
        this.index = index;
    }

    //Get the correct sprite set based on the player's state, and retrieve the correct run frame from that array
    public static PlayerSprite getRunSprite(PowerUpState state, int runSpriteFrame) {
        return runSprites.get(state)[runSpriteFrame];
    }

    //Get the correct sprite when we have a star item
    public static PlayerSprite getStarSprite(PowerUpState state, int starFrame, PlayerSprite currentSprite) {
        return starSpriteCollection.get(state).get((starFrame % 24) / 6).get(currentSprite.index);
    }
}
