package com.nate.mario.level;

import java.util.ArrayList;
import java.util.List;

import com.nate.mario.entity.Entity;

public class Level {

    protected int[][] tiles;
    protected List<Entity> enemies = new ArrayList<>();
    protected Entity player;

    public static int getLevelCount() {
        return 32; //add code to dynamically detect how many levels we have
    }
}
