package com.nate.mario.item;

import java.util.Map;

import com.nate.mario.gfx.ItemSprite;

public class Item {

    // public static List<Item> items = new ArrayList<>() {{
        // add (new CoinItem(255, ItemSprite.COIN));
        // add (new MushroomItem(100, ItemSprite.MUSHROOM));
        // add (new FireFlowerItem(200, ItemSprite.FIRE_FLOWER));
    // }};

    public static Map<String, Item> items = Map.of(
        "coin", new CoinItem(255, ItemSprite.COIN),
        "mushroom", new MushroomItem(100, ItemSprite.MUSHROOM),
        "fire_flower", new FireFlowerItem(-1, ItemSprite.FIRE_FLOWER)
    );

    protected final int id;
    protected ItemSprite sprite;
    protected ItemSprite[] sprites;

    protected float x, y;
    protected boolean toBeDeleted = false;

    public Item(int id, ItemSprite sprite) {
        this.id = id;
        this.sprite = sprite;
    }

    public Item(int id, ItemSprite[] sprites) {
        this.id = id;
        this.sprites = sprites;
    }

    public Item(float x, float y, int id, ItemSprite sprite) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.sprite = sprite;
    }

    public Item(float x, float y, int id, ItemSprite[] sprites) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.sprites = sprites;
    }

    public Item newItem(float x, float y, int id, ItemSprite sprite) { return null; }
    public Item newItem(float x, float y, int id, ItemSprite[] sprites) { return null; }

    public int getID() { return id; }
    public ItemSprite getSprite() { return sprite; }
    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isToBeDeleted() { return toBeDeleted; }

    public void setToBeDeleted() { toBeDeleted = true; }
}
