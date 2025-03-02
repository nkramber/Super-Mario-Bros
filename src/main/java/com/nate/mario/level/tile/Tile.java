package com.nate.mario.level.tile;

import java.util.ArrayList;
import java.util.List;

import com.nate.mario.level.Level;

public abstract class Tile {

    public static List<Tile> tiles = new ArrayList<>() {{
        add(new SkyTile(-1, -1));
        add(new GroundTile(-1, -1));
        add(new ItemBlockTile(-1, -1));
        add(new EmptyItemBlockTile(-1, -1));
    }};

    protected int xTile, yTile;
    protected boolean tickable = false;
    protected boolean toBeDeleted = false;
    protected boolean animating = false;
    protected boolean solid = true;

    public Tile() {}

    public Tile(int xTile, int yTile) {
        this.xTile = xTile;
        this.yTile = yTile;
    }

    public void tick(Level level) { tick(); }
    public void tick() {}

    public void toBeDeleted() { toBeDeleted = true; }

    public boolean isToBeDeleted() { return toBeDeleted; }

    public abstract Tile newTile(int xTile, int yTile);
    public abstract String getName();
    public abstract int getID();

    public boolean isSolid() { return solid; }
    public boolean isAnimating() { return animating; }
    public boolean isTickable() { return tickable; }
    public int getxTile() { return xTile; }
    public int getyTile() { return yTile; }
}
