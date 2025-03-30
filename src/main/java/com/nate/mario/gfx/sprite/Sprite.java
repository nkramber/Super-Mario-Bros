package com.nate.mario.gfx.sprite;

import com.nate.mario.Main;

public class Sprite {

    public static final int TILE_HEIGHT = 16;
    public static final int TILE_WIDTH = 16;

    private static int FLICKER_SPRITE_TICK_COUNT = 8;
    private static int FLICKER_SPRITE_LENGTH = 7;

    protected final String name;
    protected final int height;

    public Sprite(String name) {
        this.name = name;
        this.height = 1;
    }

    public Sprite(String name, int height) {
        this.name = name;
        this.height = height;
    }

    public String getName() { return name; }
    public int getHeight() { return height; }

    public static int getFlickerSprite() {
        return Main.getTickCount() % (FLICKER_SPRITE_TICK_COUNT * FLICKER_SPRITE_LENGTH) / FLICKER_SPRITE_TICK_COUNT;
    }
}
