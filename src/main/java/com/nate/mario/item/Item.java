package com.nate.mario.item;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {

    public static List<Item> items = new ArrayList<>() {{
        add (new CoinItem(255, "coin"));
        add (new MushroomItem(100, "mushroom"));
    }};

    protected final int id;
    protected final String name;

    protected float x, y;
    protected boolean toBeDeleted = false;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item(float x, float y, int id, String name) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.name = name;
    }

    public abstract Item newItem(float x, float y, int id, String name);

    public int getID() { return id; }
    public String getName() { return name; }
    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isToBeDeleted() { return toBeDeleted; }

    public void toBeDeleted() { toBeDeleted = true; }
}
