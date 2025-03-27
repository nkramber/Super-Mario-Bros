package com.nate.mario.level.tile;

import java.util.Map;

import com.nate.mario.level.Level;
import com.nate.mario.level.tile.animatedtile.BreakableTile;

public abstract class Tile {

    public static Map<Integer, Tile> tiles = Map.of(
        0, new SkyTile(-1, -1),
        60, new ItemBlockTile(-1, -1),
        150, new BreakableTile(-1, -1),
        255, new GroundTile(-1, -1)
    );

    protected int xTile, yTile;
    protected boolean tickable = false;
    protected boolean toBeDeleted = false;
    protected boolean solid = true;

    public Tile() {}

    public Tile(int xTile, int yTile) {
        this.xTile = xTile;
        this.yTile = yTile;
    }

    public void tick(Level level) { tick(); }
    public void tick() {}

    public void setToBeDeleted() {
        toBeDeleted = true;
    }

    public boolean isToBeDeleted() { return toBeDeleted; }

    public abstract Tile newTile(int xTile, int yTile);
    public abstract String getName();
    public abstract int getID();

    public boolean isSolid() { return solid; }
    public boolean isTickable() { return tickable; }
    public int getxTile() { return xTile; }
    public int getyTile() { return yTile; }
}
