package com.nate.mario.entity;

import java.awt.Rectangle;
import java.util.HashSet;

import com.nate.mario.gfx.EntitySprite;
import com.nate.mario.gfx.Screen;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.Tile;

public class Entity {

    protected float x, y;
    protected float xDir, yDir;
    protected int width, height;
    protected int jumpTick = 0;
    protected EntitySprite currentSprite;
    protected boolean onGround = false;

    public Entity(float xTile, float yTile, float xDir, float yDir, int width, EntitySprite currentSprite) {
        this.x = xTile * 16;
        this.y = yTile * 16;
        this.xDir = xDir;
        this.yDir = yDir;
        this.width = width * 16;
        this.height = currentSprite.getHeight() * 16;
        this.currentSprite = currentSprite;
    }

    public void getMovement(boolean[] keys, Level level) {}

    public void move() {
        x += xDir;
        y += yDir;
    }

    public void render(Screen screen) {
        screen.drawSprite(currentSprite, (int) x, (int) y);
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
                        if (tile.getyTile() * 16 < newY) {
                            if (this instanceof Player) {
                                Player player = (Player) this;
                                player.topTileCollide(tile);
                            }

                            newY = tile.getyTile() * 16 + 16 - yOffset;
                            jumpTick = 0;
                        } else {
                            newY = tile.getyTile() * 16 - height;
                            onGround = true;
                        }

                        verticalEntityRect = new Rectangle((int) (newX) + xOffset, (int) (newY + yDir + yOffset), width - xOffset * 2, height - yOffset);
                        if (horizontalEntityRect.intersects(tileRect)) {
                            continue;
                        }
                    }
    
                    if (horizontalEntityRect.intersects(tileRect)) {
                        xDir = 0;
                        if (tile.getxTile() * 16 < newX) newX = tile.getxTile() * 16 + 16 - xOffset;
                        else newX = tile.getxTile() * 16 - width + xOffset;
                        horizontalEntityRect = new Rectangle((int) (newX + xDir + xOffset), (int) y + yOffset, width - xOffset * 2, height - yOffset);
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

    public float getX() { return x; }
    public float getY() { return y; }
    public float getxDir() { return xDir; }
    public float getyDir() { return yDir; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public EntitySprite getSprite() { return currentSprite; }
}
