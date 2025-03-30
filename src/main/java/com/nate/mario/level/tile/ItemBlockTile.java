package com.nate.mario.level.tile;

import com.nate.mario.item.Item;
import com.nate.mario.level.Level;

public abstract class ItemBlockTile extends Tile {

    protected Item item;
    protected boolean createItem = false;

    public ItemBlockTile(int xTile, int yTile) {
        super(xTile, yTile);
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
        if (readyToCreateItem()) {
            level.createItem(getItemToCreate(), getxTile(), getyTile());
            item = null;
        }
    }

    public void addItemToItemBlock(Item item) {
        this.item = item;
    }

    public void createItem() { createItem = true; }

    public boolean readyToCreateItem() {
        if (createItem) {
            createItem = false;
            return true;
        }
        return false;
    }
    
    public boolean containsItem() { return item != null; }
    public Item getItemToCreate() { return item; }
}
