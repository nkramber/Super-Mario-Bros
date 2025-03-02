package com.nate.mario.level.tile;

import com.nate.mario.item.Item;
import com.nate.mario.level.Level;

public class ItemBlockTile extends Tile {

    private static final String NAME = "item_block_tile";
    private static final int ID = 60;

    private Item item;
    private boolean createItem = false;

    public ItemBlockTile(int xTile, int yTile) {
        super(xTile, yTile);
        tickable = true;
    }
    
    @Override
    public Tile newTile(int xTile, int yTile) {
        return new ItemBlockTile(xTile, yTile);
    }

    @Override
    public void tick(Level level) {
        if (readyToCreateItem()) level.createItem(getItemToCreate(), getxTile(), getyTile());
    }

    public void addItemToItemBlock(Item item) {
        this.item = item;
    }

    @Override public String getName() { return NAME; }
    @Override public int getID() { return ID; }

    public boolean containsItem() { return item != null; }
    public boolean readyToCreateItem() { return createItem; }
    public Item getItemToCreate() { return item; }

    public void createItem() { createItem = true; }
}
