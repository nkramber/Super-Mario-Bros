package com.nate.mario.level.tile;

import com.nate.mario.gfx.sprite.Sprite;

public class GroundTile extends Tile {

    private static final Sprite SPRITE = new Sprite("ground_tile");
    private static final int ID = 255;
    
    public GroundTile(int x, int y) {
        super(x, y);
        tickable = false;
    }

    @Override
    public Tile newTile(int x, int y) {
        return new GroundTile(x, y);
    }

    @Override public Sprite getSprite() { return SPRITE; }
    @Override public int getID() { return ID; }
}
