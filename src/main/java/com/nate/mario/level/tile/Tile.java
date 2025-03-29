package com.nate.mario.level.tile;

import java.util.Map;

import com.nate.mario.level.Level;

public abstract class Tile {

    public static Map<Integer, Tile> tiles = Map.of(
        0, new SkyTile(-1, -1),
        60, new QMarkBlockTile(-1, -1),
        150, new BreakableTile(-1, -1),
        255, new GroundTile(-1, -1)
    );

    protected int xTile, yTile;
    protected boolean tickable = true;
    protected boolean toBeDeleted = false;
    protected boolean solid = true;

    //Variabled for animated tiles
    protected boolean animating = false;
    protected boolean animatingDown = false;
    protected int animationFrame;

    public Tile() {}

    public Tile(int xTile, int yTile) {
        this.xTile = xTile;
        this.yTile = yTile;
    }

    public void tick(Level level) {
        if (isAnimating()) {
            if (animationFrame < 0) {
                resetAnimationState();
            } else if (animatingDown) {
                animationFrame--;
            } else if (!animatingDown) {
                if (animationFrame == 4) animatingDown = true;
                else animationFrame++;
            }
        }
    }

    public void doBottomCollision(boolean playerIsSmall) {}

    private void resetAnimationState() {
        animationFrame = 0;
        animatingDown = false;
        animating = false;
    }

    public void setToBeDeleted() {
        toBeDeleted = true;
    }

    public boolean isToBeDeleted() { return toBeDeleted; }

    public abstract Tile newTile(int xTile, int yTile);
    public abstract String getName();
    public abstract int getID();

    public void setAnimating() { animating = true; }

    public boolean isAnimating() { return animating; }
    public boolean isSolid() { return solid; }
    public boolean isTickable() { return tickable; }
    public int getxTile() { return xTile; }
    public int getyTile() { return yTile; }
    public int getAnimationFrame() { return animationFrame; }
}
