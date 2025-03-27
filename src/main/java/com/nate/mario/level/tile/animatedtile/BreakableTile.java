package com.nate.mario.level.tile.animatedtile;

import com.nate.mario.level.tile.Tile;

public class BreakableTile extends AnimatedTile {

    private static final String NAME = "breakable_tile";
    private static final int ID = 150;

    public BreakableTile(int tileX, int tileY) {
        super(tileX, tileY);
        tickable = true;
    }

    @Override
    public Tile newTile(int tileX, int tileY) {
        return new BreakableTile(tileX, tileY);
    }

    @Override public String getName() { return NAME; }
    @Override public int getID() { return ID; }
}
