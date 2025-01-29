package com.nate.mario.level.tile;

public class GroundTile extends Tile {

    private static final String NAME = "ground";

    public GroundTile(int r, int g) {
        super(NAME, r, g, true);
    }

    public GroundTile(int x, int y, String name) {
        super(x, y, name);
    }

    @Override
    public Tile newTile(int x, int y, String name) {
        return new GroundTile(x, y, name);
    }
}
