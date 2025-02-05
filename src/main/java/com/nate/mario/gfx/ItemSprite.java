package com.nate.mario.gfx;

public class ItemSprite {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    public static final ItemSprite COIN = new ItemSprite("coin");
    public static final ItemSprite MUSHROOM = new ItemSprite("mushroom");
    public static final ItemSprite[] FIRE_FLOWER = {
        new ItemSprite("fire_flower_1"),
        new ItemSprite("fire_flower_2"),
        new ItemSprite("fire_flower_3"),
        new ItemSprite("fire_flower_4")
    };

    private final String name;

    public ItemSprite(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
