package com.nate.mario.level.tile;

public class ItemBlockTile extends Tile {

    public ItemBlockTile(int id, String name, boolean solid) {
        super(id, name, solid);
    }
    
    public ItemBlockTile(int x, int y, int id, String name, boolean solid) {
        super(x, y, id, name, solid);
    }
    
    @Override
    public Tile newTile(int x, int y, int id, String name, boolean solid) {
        return new ItemBlockTile(x, y, id, name, solid);
    }

    public void toBeDeleted() {
        toBeDeleted = true;
    }
}
