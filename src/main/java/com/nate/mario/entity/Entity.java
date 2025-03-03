package com.nate.mario.entity;

import java.awt.Rectangle;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.nate.mario.entity.player.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.sprite.Sprite;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.Tile;
import com.nate.mario.util.Collision;

public abstract class Entity {

    public static Map<String, Entity> entities = Map.of(
        "goomba", new Goomba(-1, -1)
    );

    private static final float VER_ACCEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 4.0f;

    private boolean isToBeDeleted;

    protected float x, y;
    protected float xDir, yDir;
    protected int width = 16;
    protected int height;
    protected int jumpTick = 0;
    protected Sprite currentSprite;
    protected boolean onGround;
    protected boolean falling;
    protected boolean facingLeft;

    public Entity(float xTile, float yTile, float xDir, float yDir, Sprite currentSprite) {
        this.x = xTile * 16;
        this.y = yTile * 16;
        this.xDir = xDir;
        this.yDir = yDir;
        this.height = currentSprite.getHeight() * 16;
        this.currentSprite = currentSprite;
    }

    public void tick(Level level) {
        getMovement();
        doTileCollisions(Collision.getLocalEntityCollisionTiles(this, level.getTiles()));
        doEntityCollisions(level.getEntities());
        move();
    }

    public void getMovement() {
        if (!onGround) {
            if (yDir + VER_ACCEL_RATE > VER_MAX_SPEED) yDir = VER_MAX_SPEED;
            else yDir += VER_ACCEL_RATE;
        }
    }

    public void move() {
        x += xDir;
        y += yDir;
    }

    public void render(Screen screen) {
        screen.drawSprite(currentSprite, facingLeft, (int) x, (int) y);
    }

    public void doTileCollisions(Tile[][] tiles) {
        int yOffset = 4;
        int xOffset = 2;

        float newX = x;
        float newY = y;

        Rectangle verticalEntityRect = new Rectangle((int) (x) + xOffset, (int) (y + yDir + yOffset), width - xOffset * 2, height - yOffset);
        Rectangle horizontalEntityRect = new Rectangle((int) (x + xDir + xOffset), (int) (y + yOffset), width - xOffset * 2, height - yOffset);
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
                        yDir = 0;
                        if (tile.getyTile() * 16 < newY) { //Collide with tile above
                            yDir = 0;
                            newY = tile.getyTile() * 16 + 16 - yOffset;
                            jumpTick = 0;
                        } else { //Collide with tile below
                            newY = tile.getyTile() * 16 - height;
                            onGround = true;
                        }

                        verticalEntityRect = new Rectangle((int) (newX) + xOffset, (int) (newY + yOffset), width - xOffset * 2, height - yOffset);
                    }
    
                    if (horizontalEntityRect.intersects(tileRect)) {
                        xDir = 0 - xDir;
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

    public void doEntityCollisions(List<Entity> entities) {
        int xOffset = 3;

        for (Entity entity : entities) {
            if (entity.equals(this)) continue;
            Rectangle localCollisionRectangle = new Rectangle((int) getX() + xOffset, (int) getY(), getWidth() - xOffset * 2, getHeight());
            Rectangle otherCollisionRectangle = new Rectangle((int) entity.getX() + xOffset, (int) entity.getY(), entity.getWidth() - xOffset * 2, entity.getHeight());

            if (localCollisionRectangle.intersects(otherCollisionRectangle)) {
                if (entity instanceof Player) continue;
                xDir = -xDir;
                entity.setxDir(-entity.getxDir());
            }
        }
    }

    public abstract Entity newEntity(int xTile, int yTile);
    public abstract int getID();

    public void setToBeDeleted() { isToBeDeleted = true; }
    public void setxDir(float xDir) { this.xDir = xDir; }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getxDir() { return xDir; }
    public float getyDir() { return yDir; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isToBeDeleted() { return isToBeDeleted; }
    public Sprite getSprite() { return currentSprite; }
}
