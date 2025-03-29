package com.nate.mario.level.tile;

public class SkyTile extends Tile {

    public static final String NAME = "sky_tile";
    public static final int ID = 0;

    public SkyTile(int xTile, int yTile) {
        super(xTile, yTile);
        solid = false;
        tickable = false;
    }

    @Override
    public Tile newTile(int x, int y) {
        return new SkyTile(x, y);
    }

    @Override public String getName() { return NAME; }
    @Override public int getID() { return ID; }
}
