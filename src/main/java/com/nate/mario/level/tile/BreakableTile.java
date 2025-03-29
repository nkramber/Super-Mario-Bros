package com.nate.mario.level.tile;

import com.nate.mario.level.Level;

public class BreakableTile extends ItemBlockTile {

    private static final String NAME = "breakable_tile";
    private static final int ID = 150;

    public BreakableTile(int tileX, int tileY) {
        super(tileX, tileY);
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
    }

    @Override
    public void doBottomCollision(boolean playerIsSmall) {
        if (playerIsSmall) {
            if (!animating) setAnimating();
        } else {
            setToBeDeleted();
        }
    }

    @Override
    public Tile newTile(int tileX, int tileY) {
        return new BreakableTile(tileX, tileY);
    }

    @Override public String getName() { return NAME; }
    @Override public int getID() { return ID; }
}
