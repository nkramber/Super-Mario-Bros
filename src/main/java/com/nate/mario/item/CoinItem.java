package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;

public class CoinItem extends Item {

    public static final int SCORE = 200;

    public CoinItem(int id) {
        super(id);
    }

    public CoinItem(float x, float y) {
        super(x, y, ItemSprite.COIN);
    }

    @Override
    public Item newItem(float x, float y) {
        return new CoinItem(x, y);
    }
}
