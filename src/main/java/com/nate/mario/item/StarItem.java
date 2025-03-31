package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.powerupitem.PowerUpItem;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.Tile;
import com.nate.mario.util.Collision;

public class StarItem extends PowerUpItem {

    public static final int ID = 200;
    public static final ItemSprite[] SPRITE = ItemSprite.STAR;

    public StarItem(float x, float y) {
        super(x, y, true);
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
        if (isSpawning) return;

        if (!onGround) {
            if (dirY + VER_ACCEL_RATE > VER_MAX_SPEED) dirY = VER_MAX_SPEED;
            else dirY += VER_ACCEL_RATE;
        }

        doTileCollisions(Collision.getLocalItemCollisionTiles(this, level.getTiles()));
        x += dirX;
        y += dirY;
    }

    @Override
    protected float horizontalTileCollision(Tile tile, float newX) {
        dirX = 0 - dirX;
        if (tile.getxTile() * 16 < newX) newX = tile.getxTile() * 16 + 16;
        else newX = tile.getxTile() * 16 - 16;

        return newX;
    }

    @Override
    protected float verticalTileCollision(Tile tile, float newY) {
        if (tile.getyTile() * 16 < newY) {
            dirY = 0 - dirY / 3.5f;
            return tile.getyTile() * 16 + 16;
        }

        if (!onGround) dirY = -4.6f;
        else dirY = 0;
        return tile.getyTile() * 16 - 16;
    }

    @Override public Item newItem(float x, float y) { return new StarItem(x, y); }
    @Override public ItemSprite getSprite() { return SPRITE[spriteFlickerFrame / 2]; }
}
