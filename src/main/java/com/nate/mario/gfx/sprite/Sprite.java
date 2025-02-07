package com.nate.mario.gfx.sprite;

public class Sprite {

    public static final int TILE_HEIGHT = 16;
    public static final int TILE_WIDTH = 16;

    protected final String name;
    protected final int height;

    public Sprite(String name, int height) {
        this.name = name;
        this.height = height;
    }

    public String getName() { return name; }
    public int getHeight() { return height; }
}
