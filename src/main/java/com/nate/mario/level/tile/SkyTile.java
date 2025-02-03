package com.nate.mario.level.tile;

public class SkyTile extends Tile {

    public SkyTile(int id, String name, boolean solid) {
        super(id, name, solid);
    }

    public SkyTile(int xTile, int yTile, int id, String name, boolean solid) {
        super(xTile, yTile, id, name, solid);
    }

    @Override
    public Tile newTile(int x, int y, int id, String name, boolean solid) {
        return new SkyTile(x, y, id, name, solid);
    }
}
