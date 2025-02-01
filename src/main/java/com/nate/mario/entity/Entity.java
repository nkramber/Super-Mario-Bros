package com.nate.mario.entity;

import java.awt.Rectangle;
import java.util.HashSet;

import com.nate.mario.gfx.Screen;
import com.nate.mario.level.tile.Tile;

public class Entity {

    protected float x, y;
    protected float xDir, yDir;
    protected int width, height;
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

    public void getMovement() {}
    public void getMovement(boolean[] keys) {}

    public void move() {
        x += xDir;
        y += yDir;
    }

    public void render(Screen screen) {
        screen.drawSprite(currentSprite, (int) x, (int) y);
    }

    public void doTileCollisions(Tile[][] tiles) {
        int yOffset = 0;
        if (yDir > 1) yOffset = 1;

        Rectangle newVertPlayerRect = new Rectangle((int) x, (int) (y + yDir - yOffset), width, height + yOffset);
        Rectangle newHoriPlayerRect = new Rectangle((int) (x + xDir), (int) y - yOffset, width, height + yOffset);
        HashSet<Tile> floorTiles = new HashSet<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile tile = tiles[i][j];
                Rectangle tileRect = new Rectangle(tile.getX() * 16, tile.getY() * 16, 16, 16);

                if (y + height == tile.getY() * 16 && ((tile.getX() * 16 >= x && tile.getX() * 16 <= x + width - 1) || (tile.getX() * 16 + 16 >= x && tile.getX() * 16 + 15 <= x + width - 1))) {
                    floorTiles.add(tile);
                }

                if (!floorTiles.isEmpty()) {
                    onGround = false;
                    for (Tile floorTile : floorTiles) {
                        if (!floorTile.isSolid()) continue;
                        onGround = true;
                    }
                }

                if (tile.isSolid()) {
                    if (newVertPlayerRect.intersects(tileRect)) {
                        yDir = 0;
                        if (tile.getY() * 16 < y) y = tile.getY() * 16 + 16;
                        else y = tile.getY() * 16 - height;
                        onGround = true;
                        newVertPlayerRect = new Rectangle((int) x, (int) (y + yDir), width, height);
    
                        if (newHoriPlayerRect.intersects(tileRect)) {
                            continue;
                        }
                    }
    
                    if (newHoriPlayerRect.intersects(tileRect)) {
                        xDir = 0;
                        if (tile.getX() * 16 < x) x = tile.getX() * 16 + 16;
                        else x = tile.getX() * 16 - width;
                        newHoriPlayerRect = new Rectangle((int) (x + xDir), (int) y, width, height);
                    }
                }
            }
        }
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getxDir() { return xDir; }
    public float getyDir() { return yDir; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
