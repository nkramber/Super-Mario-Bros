package com.nate.mario.level.tile;

public class GroundTile extends Tile {

    private static final String NAME = "ground_tile";
    private static final int ID = 255;
    
    public GroundTile(int x, int y) {
        super(x, y);
        tickable = false;
    }

    @Override
    public Tile newTile(int x, int y) {
        return new GroundTile(x, y);
    }

    @Override public String getName() { return NAME; }
    @Override public int getID() { return ID; }
}
