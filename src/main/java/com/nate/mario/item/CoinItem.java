package com.nate.mario.item;

public class CoinItem extends Item {

    public CoinItem(int id, String name) {
        super(id, name);
    }

    public CoinItem(int xTile, int yTile, int id, String name) {
        super(xTile, yTile, id, name);
    }

    @Override
    public Item newItem(int xTile, int yTile, int id, String name) {
        return new CoinItem(xTile, yTile, id, name);
    }
}
