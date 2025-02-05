package com.nate.mario.item;

import com.nate.mario.gfx.ItemSprite;

public class CoinItem extends Item {

    public CoinItem(int id, ItemSprite sprite) {
        super(id, sprite);
    }

    public CoinItem(float x, float y, int id, ItemSprite sprite) {
        super(x, y, id, sprite);
    }

    @Override
    public Item newItem(float x, float y, int id, ItemSprite sprite) {
        return new CoinItem(x, y, id, sprite);
    }
}
