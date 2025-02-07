package com.nate.mario.item;

import java.util.Map;

import com.nate.mario.gfx.sprite.ItemSprite;

public class Item {

    public static Map<String, Item> items = Map.of(
        "coin", new CoinItem(255),
        "mushroom", new MushroomItem(100),
        "fire_flower", new FireFlowerItem(-1)
    );

    protected int id;
    protected ItemSprite sprite;
    protected ItemSprite[] sprites;

    protected float x, y;
    protected boolean toBeDeleted = false;

    public Item(int id) {
        this.id = id;
    }

    public Item(float x, float y, ItemSprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public Item(float x, float y, ItemSprite[] sprites) {
        this.x = x;
        this.y = y;
        this.sprites = sprites;
    }

    public Item newItem(float x, float y) { return null; }

    public int getID() { return id; }
    public ItemSprite getSprite() { return sprite; }
    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isToBeDeleted() { return toBeDeleted; }

    public void setToBeDeleted() { toBeDeleted = true; }
}
