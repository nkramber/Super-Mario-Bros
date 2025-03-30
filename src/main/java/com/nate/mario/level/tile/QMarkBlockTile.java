package com.nate.mario.level.tile;

import com.nate.mario.gfx.sprite.Sprite;
import com.nate.mario.gfx.sprite.TileSprite;
import com.nate.mario.level.Level;

public class QMarkBlockTile extends ItemBlockTile {

    //For when the QMark block has not been hit yet
    private static final Sprite[] QMARK_SPRITE = TileSprite.QMARK_BLOCK;

    //For when the QMark block has already been hit
    private static final Sprite EMPTY_SPRITE = new Sprite("empty_item_block_tile");
    private static final int ID = 60;

    private boolean empty = false;

    public QMarkBlockTile(int xTile, int yTile) {
        super(xTile, yTile);
    }
    
    @Override
    public void tick(Level level) {
        super.tick(level);
    }

    @Override
    public void doAnimation() {
        if (animationHeight < 0) {
            super.resetAnimationState();
        } else if (animatingDown) {
            animationHeight--;
        } else if (!animatingDown) {
            if (animationHeight == 4) animatingDown = true;
            else animationHeight++;
        }
    }

    @Override
    public void doBottomCollision(boolean playerIsSmall) {
        if (!empty) {
            empty = true;
            animating = true;
            super.createItem();
        }
    }

    @Override
    public Tile newTile(int xTile, int yTile) {
        return new QMarkBlockTile(xTile, yTile);
    }

    @Override public Sprite getSprite() {
        if (!empty) return QMARK_SPRITE[Sprite.getFlickerSprite()];
        return EMPTY_SPRITE;
    }
    
    @Override public int getID() { return ID; }
}
