package com.nate.mario.item;

public class CoinItem extends Item {

    public CoinItem(int id, String name) {
        super(id, name);
    }

    public CoinItem(float x, float y, int id, String name) {
        super(x, y, id, name);
    }

    @Override
    public Item newItem(float x, float y, int id, String name) {
        return new CoinItem(x, y, id, name);
    }
}
