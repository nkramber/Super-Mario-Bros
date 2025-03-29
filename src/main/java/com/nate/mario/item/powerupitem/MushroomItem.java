package com.nate.mario.item.powerupitem;

import java.awt.Rectangle;
import java.util.HashSet;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.Item;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.Tile;
import com.nate.mario.util.Collision;

public class MushroomItem extends PowerUpItem {

    public static final int ID = 100;
    public static final ItemSprite SPRITE = ItemSprite.MUSHROOM;

    private static final float VER_ACCEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 4.0f;

    private float dirX = 0.75f;
    private float dirY = 0;
    private boolean onGround = true;

    public MushroomItem(float x, float y) {
        super(x, y);
    }

    @Override
    public void tick(Level level) {
        //If we are falling, then add gravity to the mushroom/set at terminal velocity
        if (!onGround) {
            if (dirY + VER_ACCEL_RATE > VER_MAX_SPEED) dirY = VER_MAX_SPEED;
            else dirY += VER_ACCEL_RATE;
        }

        //Don't collide with tiles while we're still rising out of our spawn block
        if (!inSpawnAnimation) doTileCollisions(Collision.getLocalItemCollisionTiles(this, level.getTiles()));

        //Move upwards if we're in our spawn animation
        if (inSpawnAnimation && (initialY - y < 16)) y -= 0.5f;
        else {
            //Done moving upwards. Begin moving sideways, adding gravity when falling, and collision with tiles
            if (inSpawnAnimation) inSpawnAnimation = false;

            x += dirX;
            y += dirY;
        }
    }

    public void doTileCollisions(Tile[][] tiles) {
        float newX = x;
        float newY = y;

        Rectangle verticalItemRect = new Rectangle((int) x, (int) (y + dirY), 16, 16);
        Rectangle horizontalItemRect = new Rectangle((int) (x + dirX), (int) y, 16, 16);
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
                        dirY = 0;
                        newY = tile.getyTile() * 16 - 16;
                        onGround = true;

                        verticalItemRect = new Rectangle((int) (newX), (int) (newY), 16, 16);
                    }

                    if (horizontalItemRect.intersects(tileRect)) {
                        dirX = 0 - dirX;
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

    @Override public Item newItem(float x, float y) { return new MushroomItem(x, y); }
    @Override public int getID() { return ID; }
    @Override public ItemSprite getSprite() { return SPRITE; }
}
