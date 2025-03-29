package com.nate.mario.level.tile;

import com.nate.mario.level.Level;

public class QMarkBlockTile extends ItemBlockTile {

    //For when the QMark block has not been hit yet
    private static final String UNUSED_NAME = "qmark_block_tile";
    //For when the QMark block has already been hit
    private static final String USED_NAME = "empty_item_block_tile";
    private static final int ID = 60;

    private boolean unused = true;

    public QMarkBlockTile(int xTile, int yTile) {
        super(xTile, yTile);
    }
    
    @Override
    public void tick(Level level) {
        super.tick(level);
    }

    @Override
    public void doBottomCollision(boolean playerIsSmall) {
        if (unused) {
            unused = false;
            animating = true;
            createItem();
        }
    }

    @Override
    public Tile newTile(int xTile, int yTile) {
        return new QMarkBlockTile(xTile, yTile);
    }

    @Override public String getName() {
        if (unused) return UNUSED_NAME;
        else return USED_NAME;
    }
    @Override public int getID() { return ID; }
}
