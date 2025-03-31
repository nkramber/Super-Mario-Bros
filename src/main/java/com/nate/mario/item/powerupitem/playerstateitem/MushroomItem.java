package com.nate.mario.item.powerupitem.playerstateitem;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.Item;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.Tile;
import com.nate.mario.util.Collision;

public class MushroomItem extends PlayerStateItem {

    public static final int ID = 100;
    public static final ItemSprite SPRITE = ItemSprite.MUSHROOM;

    private static final float VER_ACCEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 4.0f;

    public MushroomItem(float x, float y) {
        super(x, y, false);
        dirX = 0.75f;
        dirY = 0f;
    }

    @Override
    public void tick(Level level) {
        super.tick(level);

        //Don't collide with tiles or move while we're still rising out of our spawn block
        if (!isSpawning) {
            //If we are falling, then add gravity to the mushroom/set at terminal velocity
            if (!onGround) {
                if (dirY + VER_ACCEL_RATE > VER_MAX_SPEED) dirY = VER_MAX_SPEED;
                else dirY += VER_ACCEL_RATE;
            }

            doTileCollisions(Collision.getLocalItemCollisionTiles(this, level.getTiles()));
            x += dirX;
            y += dirY;
        }
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
        dirY = 0;
        onGround = true;
        return tile.getyTile() * 16 - 16;
    }

    @Override public Item newItem(float x, float y) { return new MushroomItem(x, y); }
    @Override public ItemSprite getSprite() { return SPRITE; }
}
