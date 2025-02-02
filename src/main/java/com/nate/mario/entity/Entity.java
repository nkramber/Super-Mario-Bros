package com.nate.mario.entity;

import java.awt.Color;
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

        for (Tile tile : floorTiles2) screen.drawRect(Color.WHITE, new Rectangle(tile.getX() * 16, tile.getY() * 16, 16, 16));
    }

    public void doTileCollisions(Tile[][] tiles) {
        int yOffset = 0;
        if (yDir > 1.0f) yOffset = 1;

        float newX = x;
        float newY = y;

        Rectangle newVertPlayerRect = new Rectangle((int) (x), (int) (y + yDir - yOffset), width, height + yOffset);
        Rectangle newHoriPlayerRect = new Rectangle((int) (x + xDir), (int) (y - yOffset), width, height + yOffset);
        HashSet<Tile> floorTiles = new HashSet<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile tile = tiles[i][j];
                Rectangle tileRect = new Rectangle(tile.getX() * 16, tile.getY() * 16, 16, 16);
                Rectangle tileFloorObserverRect = new Rectangle(tile.getX() * 16, tile.getY() * 16 - 1, 16, 16);

                if (tile.getY() * 16 == y + height && newVertPlayerRect.intersects(tileFloorObserverRect)) {
                    floorTiles.add(tile);
                }

                if (tile.isSolid()) {
                    if (newVertPlayerRect.intersects(tileRect)) {
                        yDir = 0;
                        if (tile.getY() * 16 < newY) newY = tile.getY() * 16 + 16;
                        else newY = tile.getY() * 16 - height;
                        onGround = true;
                        newVertPlayerRect = new Rectangle((int) (newX), (int) (newY + yDir - yOffset), width, height + yOffset);
    
                        if (newHoriPlayerRect.intersects(tileRect)) {
                            continue;
                        }
                    }
    
                    if (newHoriPlayerRect.intersects(tileRect)) {
                        xDir = 0;
                        if (tile.getX() * 16 < newX) newX = tile.getX() * 16 + 16;
                        else newX = tile.getX() * 16 - width;
                        newHoriPlayerRect = new Rectangle((int) (newX + xDir), (int) y - yOffset, width, height + yOffset);
                    }
                }
            }
        }

        floorTiles2 = floorTiles;
        for (Tile tile : floorTiles) {
            System.out.println(tile.getX() + ", " + tile.getY());
        }

        if (!floorTiles.isEmpty()) {
            onGround = false;
            for (Tile floorTile : floorTiles) {
                if (!floorTile.isSolid()) continue;
                onGround = true;
            }
        }

        if (floorTiles.size() == 1) {
            // System.out.println("Single tile beneath " + count++);
        }

        x = newX;
        y = newY;
    }

    HashSet<Tile> floorTiles2 = new HashSet<>();
    int count = 0;

    public float getX() { return x; }
    public float getY() { return y; }
    public float getxDir() { return xDir; }
    public float getyDir() { return yDir; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
