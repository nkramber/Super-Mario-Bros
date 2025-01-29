package com.nate.mario.level.tile;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {

    public static List<Tile> tiles = new ArrayList<>() {{
        add(new SkyTile(0, 0));
        add(new GroundTile(255, 0));
    }};

    public String name;
    public int redID, greenID, blueID;
    public boolean solid;

    public Tile(String name, int redID, int greenID, boolean solid) {
        this.name = name;
        this.redID = redID;
        this.greenID = greenID;
        this.solid = solid;
    }

    protected int x, y;

    public Tile(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public abstract Tile newTile(int xTile, int yTile, String name);
}
