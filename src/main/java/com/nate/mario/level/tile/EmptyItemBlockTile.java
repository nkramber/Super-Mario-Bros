package com.nate.mario.level.tile;

public class EmptyItemBlockTile extends Tile {

    private static final String NAME = "empty_item_block_tile";
    private static final int ID = -1;

    private int animationFrame = 0;
    private boolean animatingDown = false;
    
    public EmptyItemBlockTile(int tileX, int tileY) {
        super(tileX, tileY);
        tickable = true;
        animating = true;
    }
    
    @Override
    public Tile newTile(int tileX, int tileY) {
        return new ItemBlockTile(tileX, tileY);
    }

    @Override
    public void tick() {
        if (isAnimating()) {
            if (animationFrame < 0) {
                animating = false;
            } else if (animatingDown) {
                animationFrame--;
            } else if (!animatingDown) {
                if (animationFrame == 4) animatingDown = true;
                else animationFrame++;
            }
        } else stopTicking();
    }

    @Override public String getName() { return NAME; }
    @Override public int getID() { return ID; }
        
    public int getAnimationFrame() { return animationFrame; }
}
