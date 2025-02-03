package com.nate.mario.level.tile;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {

    public static List<Tile> tiles = new ArrayList<>() {{
        add(new SkyTile(0, "sky", false));
        add(new GroundTile(255, "ground", true));
        add(new ItemBlockTile(60, "itemblock", true));
        add(new EmptyItemBlockTile(-1, "emptyitemblock", false));
    }};

    protected final int id;
    protected final String name;
    protected final boolean solid;

    protected int xTile, yTile;
    protected boolean toBeDeleted = false;
    protected boolean animating = false;

    public Tile(int id, String name, boolean solid) {
        this.id = id;
        this.name = name;
        this.solid = solid;
    }

    public Tile(int xTile, int yTile, int id, String name, boolean solid) {
        this.xTile = xTile;
        this.yTile = yTile;
        this.id = id;
        this.name = name;
        this.solid = solid;
    }

    public boolean isToBeDeleted() { return toBeDeleted; }

    public abstract Tile newTile(int xTile, int yTile, int id, String name, boolean solid);

    public int getID() { return id; }
    public String getName() { return name; }
    public boolean isSolid() { return solid; }
    public boolean isAnimating() { return animating; }
    public int getxTile() { return xTile; }
    public int getyTile() { return yTile; }
}
