package com.nate.mario.level.tile;

public class GroundTile extends Tile {
    
    public GroundTile(int id, String name, boolean solid) {
        super(id, name, solid);
    }
    
    public GroundTile(int x, int y, int id, String name, boolean solid) {
        super(x, y, id, name, solid);
    }

    @Override
    public Tile newTile(int x, int y, int id, String name, boolean solid) {
        return new GroundTile(x, y, id, name, solid);
    }
}
