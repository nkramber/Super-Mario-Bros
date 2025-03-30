package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.gfx.sprite.Sprite;

public class CoinItem extends Item {

    public static final int ID = 255;
    private static final ItemSprite[] SPRITE = ItemSprite.COIN;

    public static final int SCORE = 200;

    public CoinItem(float x, float y) {
        super(x, y);
    }

    @Override public Item newItem(float x, float y) { return new CoinItem(x, y); }
    @Override public int getID() { return ID; }
    @Override public ItemSprite getSprite() { return SPRITE[Sprite.getFlickerSprite()]; }
}
