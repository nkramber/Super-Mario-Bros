package com.nate.mario.level.tile;

public class SkyTile extends Tile {

    public static final String NAME = "sky";

    public SkyTile(int r, int g) {
        super(NAME, r, g, false);
    }

    public SkyTile(int x, int y, String name) {
        super(x, y, name);
    }

    @Override
    public Tile newTile(int x, int y, String name) {
        return new SkyTile(x, y, name);
    }
}
