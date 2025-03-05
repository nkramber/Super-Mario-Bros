package com.nate.mario.entity;

import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Map;

import com.nate.mario.entity.player.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.sprite.Sprite;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.Tile;
import com.nate.mario.util.Collision;

public abstract class Entity {

    //Blue color code, entity type
    public static Map<Integer, Entity> entities = Map.of(
        255, new Goomba(-1, -1)
    );

    private static final float VER_ACCEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 4.0f;

    private boolean isToBeDeleted;

    protected float x, y;
    protected float dirX, dirY;
    protected int width = 16;
    protected int height;
    protected int jumpTick = 0;
    protected int animationFrame = 0;
    protected Sprite currentSprite;
    protected Rectangle collisionRect;
    protected boolean inDyingAnimation;
    protected boolean onGround;
    protected boolean falling;
    protected boolean facingLeft;
    protected boolean collidable = true;

    public Entity(float xTile, float yTile, float dirX, float dirY, Sprite currentSprite) {
        this.x = xTile * 16;
        this.y = yTile * 16;
        this.dirX = dirX;
        this.dirY = dirY;
        this.height = currentSprite.getHeight() * 16;
        this.currentSprite = currentSprite;
        collisionRect = new Rectangle((int) x, (int) y, width, height);
    }

    public void tick(Level level, boolean[] keys) { tick(level); }
    public void tick(Level level) {
        updateSprite();
        doMovement();
        collisionRect = new Rectangle((int) (x + dirX), (int) (y + dirY), width, height);
        doTileCollisions(Collision.getLocalEntityCollisionTiles(this, level.getTiles()));
    }

    protected void updateSprite() {}

    protected void doMovement(Level level, boolean[] keys) { doMovement(); }
    protected void doMovement() {
        x += dirX;
        y += dirY;

        if (!onGround) {
            if (dirY + VER_ACCEL_RATE > VER_MAX_SPEED) dirY = VER_MAX_SPEED;
            else dirY += VER_ACCEL_RATE;
        }
    }

    public void render(Screen screen) {
        screen.drawSprite(currentSprite, facingLeft, (int) x, (int) y);
    }

    public void entityCollide(Entity entity) {
        if (entity instanceof Player) {
            //If the player collided with us from above (player y is above the entity y, player is falling)
            if (entity.getY() + 2 < y && entity.getDirY() > 0) {
                setInDyingAnimation();
            }
        } else {
            dirX = -dirX;
        }
    }

    protected void doTileCollisions(Tile[][] tiles) {
        int yOffset = 4;
        int xOffset = 2;

        float newX = x;
        float newY = y;

        Rectangle verticalEntityRect = new Rectangle((int) (x) + xOffset, (int) (y + dirY + yOffset), width - xOffset * 2, height - yOffset);
        Rectangle horizontalEntityRect = new Rectangle((int) (x + dirX + xOffset), (int) (y + yOffset), width - xOffset * 2, height - yOffset);
        HashSet<Tile> floorTiles = new HashSet<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile tile = tiles[i][j];
                Rectangle tileRect = new Rectangle(tile.getxTile() * 16, tile.getyTile() * 16, 16, 16);
                Rectangle tileFloorObserverRect = new Rectangle(tile.getxTile() * 16, tile.getyTile() * 16 - 1, 16, 16);

                if (tile.getyTile() * 16 == y + height && verticalEntityRect.intersects(tileFloorObserverRect)) {
                    floorTiles.add(tile);
                }

                if (tile.isSolid()) {
                    if (verticalEntityRect.intersects(tileRect)) {
                        dirY = 0;
                        if (tile.getyTile() * 16 < newY) { //Collide with tile above
                            dirY = 0;
                            newY = tile.getyTile() * 16 + 16 - yOffset;
                            jumpTick = 0;
                        } else { //Collide with tile below
                            newY = tile.getyTile() * 16 - height;
                            onGround = true;
                        }

                        verticalEntityRect = new Rectangle((int) (newX) + xOffset, (int) (newY + yOffset), width - xOffset * 2, height - yOffset);
                    }
    
                    if (horizontalEntityRect.intersects(tileRect)) {
                        dirX = 0 - dirX;
                        if (tile.getxTile() * 16 < newX) { //Collide with tile to the left
                            newX = tile.getxTile() * 16 + 16 - xOffset;
                        } else { //Collide with tile to the right
                            newX = tile.getxTile() * 16 - width + xOffset;
                        }
                        
                        horizontalEntityRect = new Rectangle((int) (newX + xOffset), (int) y + yOffset, width - xOffset * 2, height - yOffset);
                    }
                }
            }
        }

        if (!floorTiles.isEmpty()) {
            onGround = false;
            falling = true;
            for (Tile floorTile : floorTiles) {
                if (!floorTile.isSolid()) continue;
                onGround = true;
                falling = false;
            }
        }

        x = newX;
        y = newY;
    }

    public abstract Entity newEntity(int xTile, int yTile);
    public abstract int getID();
    public abstract int getScore();

    public void setInDyingAnimation() {
        inDyingAnimation = true;
        collidable = false;
    }
    public void setToBeDeleted() { isToBeDeleted = true; }
    public void setDirX(float xDir) { this.dirX = xDir; }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getDirX() { return dirX; }
    public float getDirY() { return dirY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isToBeDeleted() { return isToBeDeleted; }
    public boolean isCollidable() { return collidable; }
    public Sprite getSprite() { return currentSprite; }
    public Rectangle getCollisionRect() { return collisionRect; }
}
