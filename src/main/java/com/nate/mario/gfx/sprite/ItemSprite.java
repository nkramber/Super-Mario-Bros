package com.nate.mario.gfx.sprite;

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
    public static final ItemSprite[] ANIMATED_COIN = {
        new ItemSprite("animated_coin_1"),
        new ItemSprite("animated_coin_2"),
        new ItemSprite("animated_coin_3"),
        new ItemSprite("animated_coin_4")
    };

    private final String name;

    public ItemSprite(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
