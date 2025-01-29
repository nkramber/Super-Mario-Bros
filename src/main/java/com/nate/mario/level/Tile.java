package com.nate.mario.level;

public class Tile {

    protected int xTile, yTile;

    public Tile(int xTile, int yTile) {
        this.xTile = xTile;
        this.yTile = yTile;
    }

    public int getxTile() { return xTile; }
    public int getyTile() { return yTile; }
}
