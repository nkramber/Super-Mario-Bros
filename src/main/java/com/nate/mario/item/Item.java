package com.nate.mario.item;

import java.util.Map;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.powerupitem.playerstateitem.FireFlowerItem;
import com.nate.mario.item.powerupitem.playerstateitem.MushroomItem;
import com.nate.mario.level.Level;

public abstract class Item {

    //ID corresponds to the green color value of the pixel in the level file where the item is to be placed
    public static Map<Integer, Item> items = Map.of(
        BlockCoinItem.ID, new BlockCoinItem(-1, -1),
        CoinItem.ID, new CoinItem(-1, -1),
        MushroomItem.ID, new MushroomItem(-1, -1),
        FireFlowerItem.ID, new FireFlowerItem(-1, -1),
        StarItem.ID, new StarItem(-1, -1)
    );

    protected float x, y;
    protected boolean toBeDeleted = false;

    public Item(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(Level level) {}

    public abstract Item newItem(float x, float y);
    public abstract ItemSprite getSprite();

    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isToBeDeleted() { return toBeDeleted; }

    public void setToBeDeleted() { toBeDeleted = true; }
}
