package com.nate.mario.level.tile;

import com.nate.mario.gfx.sprite.Sprite;
import com.nate.mario.level.Level;

public class BreakableTile extends ItemBlockTile {

    private static final Sprite REGULAR_SPRITE = new Sprite("breakable_tile");
    private static final Sprite EMPTY_SPRITE = new Sprite("empty_item_block_tile");
    private static final int ID = 150;

    private boolean empty = false;

    public BreakableTile(int tileX, int tileY) {
        super(tileX, tileY);
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
        if (item != null) {
            createItem();
            animating = true;
            empty = true;
            //turn to empty item block
        } else if (!empty) {
            if (playerIsSmall && !animating) setAnimating();
            else setToBeDeleted();
        }
    }

    @Override
    public Tile newTile(int tileX, int tileY) {
        return new BreakableTile(tileX, tileY);
    }

    @Override public Sprite getSprite() {
        if (!empty) return REGULAR_SPRITE;
        return EMPTY_SPRITE;
    }
    @Override public int getID() { return ID; }
}
