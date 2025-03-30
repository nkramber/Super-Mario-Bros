package com.nate.mario.level.tile;

import java.util.Map;

import com.nate.mario.gfx.sprite.Sprite;
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
    protected int animationHeight = 0;

    public Tile() {}

    public Tile(int xTile, int yTile) {
        this.xTile = xTile;
        this.yTile = yTile;
    }

    public void tick(Level level) {
        if (isAnimating()) doAnimation();
    }

    public void doAnimation() {}

    public void doBottomCollision(boolean playerIsSmall) {}

    protected void resetAnimationState() {
        animationHeight = 0;
        animatingDown = false;
        animating = false;
    }

    public void setToBeDeleted() {
        toBeDeleted = true;
    }

    public boolean isToBeDeleted() { return toBeDeleted; }

    public abstract Tile newTile(int xTile, int yTile);
    public abstract Sprite getSprite();
    public abstract int getID();

    public void setAnimating() { animating = true; }

    public boolean isAnimating() { return animating; }
    public boolean isSolid() { return solid; }
    public boolean isTickable() { return tickable; }
    public int getxTile() { return xTile; }
    public int getyTile() { return yTile; }
    public int getAnimationHeight() { return animationHeight; }
}
