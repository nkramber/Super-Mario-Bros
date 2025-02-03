package com.nate.mario.item;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {

    public static List<Item> items = new ArrayList<>() {{
        add (new CoinItem(255, "coin"));
    }};

    protected final int id;
    protected String name;
    protected boolean toBeDeleted = false;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected int x, y;

    public Item(int x, int y, int id, String name) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.name = name;
    }

    public abstract Item newItem(int xTile, int yTile, int id, String name);

    public int getID() { return id; }
    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isToBeDeleted() { return toBeDeleted; }

    public void toBeDeleted() { toBeDeleted = true; }
}
