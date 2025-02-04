package com.nate.mario.level.tile;

import com.nate.mario.item.Item;

public class ItemBlockTile extends Tile {

    private Item item;
    private boolean createItem = false;

    public ItemBlockTile(int id, String name, boolean solid) {
        super(id, name, solid);
    }
    
    public ItemBlockTile(int xTile, int yTile, int id, String name, boolean solid) {
        super(xTile, yTile, id, name, solid);
    }
    
    @Override
    public Tile newTile(int xTile, int yTile, int id, String name, boolean solid) {
        return new ItemBlockTile(xTile, yTile, id, name, solid);
    }

    public void toBeDeleted() {
        toBeDeleted = true;
    }

    public void addItemToItemBlock(Item item) {
        this.item = item;
    }

    public boolean containsItem() { return item != null; }
    public boolean readyToCreateItem() { return createItem; }
    public Item getItemToCreate() { return item; }

    public void createItem() { createItem = true; }
}
