package com.nate.mario.item;

import java.util.Map;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.level.Level;

public abstract class Item {

    //ID corresponds to the green color value of the pixel in the level file where the item is to be placed
    public static Map<String, Item> items = Map.of(
        "coin", new CoinItem(-1, -1),
        "mushroom", new MushroomItem(-1, -1),
        "block_coin", new BlockCoin(-1, -1)
    );

    protected float x, y;
    protected boolean toBeDeleted = false;

    public Item(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(Level level) { tick(); }
    public void tick() {}

    public abstract Item newItem(float x, float y);
    public abstract int getID();
    public abstract ItemSprite getSprite();

    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isToBeDeleted() { return toBeDeleted; }

    public void setToBeDeleted() { toBeDeleted = true; }
}
