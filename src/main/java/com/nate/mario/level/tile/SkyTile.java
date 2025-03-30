package com.nate.mario.level.tile;

import com.nate.mario.gfx.sprite.Sprite;

public class SkyTile extends Tile {

    public static final Sprite SPRITE = new Sprite("sky_tile");
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

    @Override public Sprite getSprite() { return SPRITE; }
    @Override public int getID() { return ID; }
}
