package com.nate.mario.util;

import java.util.ArrayList;
import java.util.List;

import com.nate.mario.entity.Entity;
import com.nate.mario.item.BlockCoin;
import com.nate.mario.item.Item;
import com.nate.mario.level.tile.Tile;

public class Collision {

    //Calculate which items to check for collisions based on the location of the entity
    public static List<Item> getLocalEntityCollisionItems(Entity entity, List<Item> items) {
        int xBoundLeft = (int) (entity.getX() - 16);
        int xBoundRight = (int) (entity.getX() + 31);
        int yBoundTop = (int) (entity.getY() - 16);
        int yBoundBottom = (int) (entity.getY() + 31 + entity.getHeight() - 16);

        List<Item> collisionItems = new ArrayList<>();

        for (Item item : items) {
            if (item instanceof BlockCoin) continue; //Don't collide with animated coin items as they aren't collidable
            int itemX = (int) item.getX();
            int itemY = (int) item.getY();

            if (itemX > xBoundLeft && itemX < xBoundRight && itemY > yBoundTop && itemY < yBoundBottom) {
                collisionItems.add(item);
            }
        }

        return collisionItems;
    }

    //Calculate which tiles to check for collisions based on the location of the entity
    public static Tile[][] getLocalEntityCollisionTiles(Entity entity, Tile[][] tiles) {
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
    public static Tile[][] getLocalItemCollisionTiles(Item item, Tile[][] tiles) {
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
