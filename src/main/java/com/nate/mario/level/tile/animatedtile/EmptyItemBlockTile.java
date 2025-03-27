package com.nate.mario.level.tile.animatedtile;

import com.nate.mario.level.tile.ItemBlockTile;
import com.nate.mario.level.tile.Tile;

public class EmptyItemBlockTile extends AnimatedTile {

    private static final String NAME = "empty_item_block_tile";
    private static final int ID = -1;
    
    public EmptyItemBlockTile(int tileX, int tileY) {
        super(tileX, tileY);
        tickable = true;
        animating = true;
    }
    
    @Override
    public Tile newTile(int tileX, int tileY) {
        return new ItemBlockTile(tileX, tileY);
    }

    @Override public String getName() { return NAME; }
    @Override public int getID() { return ID; }
}
