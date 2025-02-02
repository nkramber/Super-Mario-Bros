package com.nate.mario.entity;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashSet;

import com.nate.mario.gfx.Screen;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.Tile;

public class Entity {

    protected float x, y;
    protected float xDir, yDir;
    protected int width, height;
    protected int jumpTick = 0;
    protected String currentSprite;
    protected boolean onGround = false;

    public Entity(float xTile, float yTile, float xDir, float yDir, int width, int height, String currentSprite) {
        this.x = xTile * 16;
        this.y = yTile * 16;
        this.xDir = xDir;
        this.yDir = yDir;
        this.width = width * 16;
        this.height = height * 16;
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
        // if (Math.abs(yDir) > 1.0f) yOffset = 1;

        float newX = x;
        float newY = y;

        Rectangle verticalEntityRect = new Rectangle((int) (x) + xOffset, (int) (y + yDir + yOffset), width - xOffset * 2, height - yOffset);
        Rectangle horizontalEntityRect = new Rectangle((int) (x + xDir + xOffset), (int) (y + yOffset), width - xOffset * 2, height - yOffset);
        HashSet<Tile> floorTiles = new HashSet<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile tile = tiles[i][j];
                Rectangle tileRect = new Rectangle(tile.getX() * 16, tile.getY() * 16, 16, 16);
                Rectangle tileFloorObserverRect = new Rectangle(tile.getX() * 16, tile.getY() * 16 - 1, 16, 16);

                if (tile.getY() * 16 == y + height && verticalEntityRect.intersects(tileFloorObserverRect)) {
                    floorTiles.add(tile);
                }

                if (tile.isSolid()) {
                    if (verticalEntityRect.intersects(tileRect)) {
                        yDir = 0;
                        if (tile.getY() * 16 < newY) {
                            newY = tile.getY() * 16 + 16 - yOffset;
                            jumpTick = 0;
                        } else {
                            newY = tile.getY() * 16 - height;
                            onGround = true;
                        }

                        verticalEntityRect = new Rectangle((int) (newX) + xOffset, (int) (newY + yDir + yOffset), width - xOffset * 2, height - yOffset);
                        if (horizontalEntityRect.intersects(tileRect)) {
                            continue;
                        }
                    }
    
                    if (horizontalEntityRect.intersects(tileRect)) {
                        xDir = 0;
                        if (tile.getX() * 16 < newX) newX = tile.getX() * 16 + 16 - xOffset;
                        else newX = tile.getX() * 16 - width + xOffset;
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
}
