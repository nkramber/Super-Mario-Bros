package com.nate.mario.item;

import java.awt.Rectangle;
import java.util.HashSet;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.level.tile.Tile;

public class MushroomItem extends PowerUpItem {

    private static final float VER_ACCEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 4.0f;

    private float xDir = 0.75f;
    private float yDir = 0;
    private boolean onGround = true;

    public MushroomItem(int id) {
        super(id);
    }

    public MushroomItem(float x, float y) {
        super(x, y, ItemSprite.MUSHROOM);
    }
    
    public Item newItem(float x, float y) {
        return new MushroomItem(x, y);
    }

    public void getMovement() {
        if (!onGround) {
            if (yDir + VER_ACCEL_RATE > VER_MAX_SPEED) yDir = VER_MAX_SPEED;
            else yDir += VER_ACCEL_RATE;
        }
    }

    public void move() {
        if (inSpawnAnimation && (initialY - y < 16)) y -= 0.5f;
        else {
            if (inSpawnAnimation) inSpawnAnimation = false;

            x += xDir;
            y += yDir;
        }
    }

    public void doTileCollisions(Tile[][] tiles) {
        float newX = x;
        float newY = y;

        Rectangle verticalItemRect = new Rectangle((int) x, (int) (y + yDir), 16, 16);
        Rectangle horizontalItemRect = new Rectangle((int) (x + xDir), (int) y, 16, 16);
        HashSet<Tile> floorTiles = new HashSet<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile tile = tiles[i][j];
                Rectangle tileRect = new Rectangle(tile.getxTile() * 16, tile.getyTile() * 16, 16, 16);
                Rectangle tileFloorObserverRect = new Rectangle(tile.getxTile() * 16, tile.getyTile() * 16 - 1, 16, 16);

                if (tile.getyTile() * 16 == y && verticalItemRect.intersects(tileFloorObserverRect)) {
                    floorTiles.add(tile);
                }

                if (tile.isSolid()) {
                    if (verticalItemRect.intersects(tileRect)) {
                        yDir = 0;
                        newY = tile.getyTile() * 16 - 16;
                        onGround = true;

                        verticalItemRect = new Rectangle((int) (newX), (int) (newY), 16, 16);
                    }

                    if (horizontalItemRect.intersects(tileRect)) {
                        xDir = 0 - xDir;
                        if (tile.getxTile() * 16 < newX) newX = tile.getxTile() * 16 + 16;
                        else newX = tile.getxTile() * 16 - 16;
                        horizontalItemRect = new Rectangle((int) (newX), (int) y, 16, 16);
                    }
                }
            }
        }

        if (!floorTiles.isEmpty()) {
            onGround = false;
            for (Tile floorTile : floorTiles) {
                if (!floorTile.isSolid()) continue;
                onGround = true;
            }
        }

        x = newX;
        y = newY;
    }
}
