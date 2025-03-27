package com.nate.mario.level.tile.animatedtile;

import com.nate.mario.level.tile.Tile;

public abstract class AnimatedTile extends Tile {

    protected boolean animating = false;
    protected boolean animatingDown = false;
    protected int animationFrame;

    public AnimatedTile(int xTile, int yTile) {
        super(xTile, yTile);
    }

    @Override
    public void tick() {
        if (isAnimating()) {
            if (animationFrame < 0) {
                resetAnimationState();
            } else if (animatingDown) {
                animationFrame--;
            } else if (!animatingDown) {
                if (animationFrame == 4) animatingDown = true;
                else animationFrame++;
            }
        } else stopAnimating();
    }

    private void resetAnimationState() {
        animationFrame = 0;
        animatingDown = false;
        animating = false;
    }

    @Override public abstract Tile newTile(int xTile, int yTile);
    @Override public abstract String getName();
    @Override public abstract int getID();

    public void setAnimating() { animating = true; }
    public void stopAnimating() { animating = false; }

    public int getAnimationFrame() { return animationFrame; }
    public boolean isAnimating() { return animating; }
}
