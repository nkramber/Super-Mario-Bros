package com.nate.mario.level.tile;

import com.nate.mario.gfx.sprite.Sprite;
import com.nate.mario.level.Level;

public class BreakableTile extends ItemBlockTile {

    private static final Sprite SPRITE = new Sprite("breakable_tile");
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

    @Override public Sprite getSprite() { return SPRITE; }
    @Override public int getID() { return ID; }
}
