package com.nate.mario.util;

import com.nate.mario.entity.Entity;
import com.nate.mario.item.Item;
import com.nate.mario.level.tile.Tile;

public class Collision {

    //Calculate which tiles to check for collisions based on the location of the entity
    public static Tile[][] getEntityCollisionTiles(Entity entity, Tile[][] tiles) {
        int xBoundLeft = (int) (entity.getX() - 16) / 16;
        int xBoundRight = (int) (entity.getX() + 31) / 16;
        int yBoundTop = (int) (entity.getY() - 16) / 16;
        int yBoundBottom = (int) (entity.getY() + 31 + entity.getHeight() - 16) / 16;

        Tile[][] collisionTiles = new Tile[xBoundRight - xBoundLeft + 1][yBoundBottom - yBoundTop + 1];

        int x = 0;
        for (int i = xBoundLeft; i <= xBoundRight; i++) {
            int y = 0;
            for (int j = yBoundTop; j <= yBoundBottom; j++) {
                collisionTiles[x][y] = tiles[i][j];
                y++;
            }
            x++;
        }

        return collisionTiles;
    }

    //Calculate which tiles to check for collisions based on the location of the item
    public static Tile[][] getItemCollisionTiles(Item item, Tile[][] tiles) {
        int xBoundLeft = (int) (item.getX() - 16) / 16;
        int xBoundRight = (int) (item.getX() + 31) / 16;
        int yBoundTop = (int) (item.getY() - 16) / 16;
        int yBoundBottom = (int) (item.getY() + 31) / 16;

        Tile[][] collisionTiles = new Tile[xBoundRight - xBoundLeft + 1][yBoundBottom - yBoundTop + 1];

        int x = 0;
        for (int i = xBoundLeft; i <= xBoundRight; i++) {
            int y = 0;
            for (int j = yBoundTop; j <= yBoundBottom; j++) {
                collisionTiles[x][y] = tiles[i][j];
                y++;
            }
            x++;
        }

        return collisionTiles;
    }
}
