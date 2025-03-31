package com.nate.mario.item.powerupitem;

import java.awt.Rectangle;
import java.util.HashSet;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.Item;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.Tile;

public abstract class PowerUpItem extends Item {

    public static final int SCORE = 1000;

    protected final boolean hasSpriteArray;
    
    protected boolean isSpawning = true;
    protected boolean onGround = true;
    protected int spriteFlickerFrame = 0;
    protected int initialY;
    protected float dirX, dirY;

    public PowerUpItem(float x, float y, boolean hasSpriteArray) {
        super(x, y);
        this.hasSpriteArray = hasSpriteArray;
        initialY = (int) y;
    }

    @Override
    public void tick(Level level) {
        if (hasSpriteArray) {
            //Update the animation frame of the item
            spriteFlickerFrame++;
            if (spriteFlickerFrame == 8) spriteFlickerFrame = 0;
        }

        //If we're still spawning, then slowly move the item towards the top of the screen until it clears the block it came from
        if (isSpawning && (initialY - y < 16)) y -= 0.5f;
        else if (isSpawning) isSpawning = false;
    }

    protected void doTileCollisions(Tile[][] tiles) {
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
                    if (verticalItemRect.intersects(tileRect)) newY = verticalTileCollision(tile, newY);
                    if (horizontalItemRect.intersects(tileRect)) newX = horizontalTileCollision(tile, newX);
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

    protected float horizontalTileCollision(Tile tile, float newX) { return 0; }
    protected float verticalTileCollision(Tile tile, float newY) { return 0; }

    @Override public abstract Item newItem(float x, float y);
    @Override public abstract ItemSprite getSprite();

    public boolean isSpawning() { return isSpawning; }
    public int getInitialY() { return initialY; }
}
