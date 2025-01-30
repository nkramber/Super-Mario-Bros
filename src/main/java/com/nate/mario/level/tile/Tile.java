package com.nate.mario.level.tile;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {

    public static List<Tile> tiles = new ArrayList<>() {{
        add(new SkyTile(0, "sky", false));
        add(new GroundTile(255, "ground", true));
    }};

    public final int id;
    public final String name;
    public final boolean solid;

    public Tile(int id, String name, boolean solid) {
        this.id = id;
        this.name = name;
        this.solid = solid;
    }
    
    protected int x, y;

    public Tile(int x, int y, int id, String name, boolean solid) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.name = name;
        this.solid = solid;
    }

    public abstract Tile newTile(int x, int y, int id, String name, boolean solid);
}
