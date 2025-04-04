package com.nate.mario.gfx.sprite;

public class ItemSprite extends Sprite {

    public static final ItemSprite MUSHROOM = new ItemSprite("mushroom");

    public static final ItemSprite[] COIN = {
        new ItemSprite("coin_1"),
        new ItemSprite("coin_1"),
        new ItemSprite("coin_2"),
        new ItemSprite("coin_3"),
        new ItemSprite("coin_2"),
        new ItemSprite("coin_1"),
        new ItemSprite("coin_1")
    };

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

    public static final ItemSprite[] STAR = {
        new ItemSprite("star_1"),
        new ItemSprite("star_2"),
        new ItemSprite("star_3"),
        new ItemSprite("star_4")
    };

    public ItemSprite(String name) {
        super(name);
    }

    public String getName() { return name; }
}
