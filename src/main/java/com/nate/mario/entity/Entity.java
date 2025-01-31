package com.nate.mario.entity;

import java.awt.Color;

import com.nate.mario.gfx.Screen;
import com.nate.mario.level.tile.Tile;

public class Entity {

    protected float x, y;
    protected float xDir, yDir;
    protected int width, height;
    protected boolean onGround = true, isMoving = false, jumping = false;
    protected String currentSprite;

    public Entity(float xTile, float yTile, float xDir, float yDir, int width, int height, String currentSprite) {
        this.x = xTile * 16;
        this.y = yTile * 16;
        this.xDir = xDir;
        this.yDir = yDir;
        this.width = width;
        this.height = height;
        this.currentSprite = currentSprite;
    }

    public void getMovement() {}
    public void getMovement(boolean[] keys) {}
    
    private Tile[][] collisionTiles;

    public void render(Screen screen) {
        screen.drawSprite(currentSprite, (int) x, (int) y);

        int collisionBoxWidth = collisionTiles.length - 1;
        int collisionBoxHeight = collisionTiles[0].length - 1;
        Tile bottomCollisionTileLeft = collisionTiles[collisionBoxWidth / 2][collisionBoxHeight];
        Tile bottomCollisionTileRight = collisionTiles[(collisionBoxWidth * 16 + 16) / 16 / 2][collisionTiles[0].length - 1];
        Tile topCollisionTileLeft = collisionTiles[collisionBoxWidth / 2][0];
        Tile topCollisionTileRight = collisionTiles[(collisionBoxWidth * 16 + 16) / 16 / 2][0];
        Tile leftCollisionTileTop = collisionTiles[0][collisionBoxHeight / 2];
        Tile leftCollisionTileBottom = collisionTiles[0][(collisionBoxHeight * 16 + 16) / 16 / 2];
        Tile rightCollisionTileTop = collisionTiles[collisionBoxWidth][collisionBoxHeight / 2];
        Tile rightCollisionTileBottom = collisionTiles[collisionBoxWidth][(collisionBoxHeight * 16 + 16) / 16 / 2];

        screen.drawRect(Color.RED, bottomCollisionTileLeft.getX() * 16, bottomCollisionTileLeft.getY() * 16, 16, 16);
        screen.drawRect(Color.RED, bottomCollisionTileRight.getX() * 16, bottomCollisionTileRight.getY() * 16, 16, 16);
        screen.drawRect(Color.BLUE, topCollisionTileLeft.getX() * 16, topCollisionTileLeft.getY() * 16, 16, 16);
        screen.drawRect(Color.BLUE, topCollisionTileRight.getX() * 16, topCollisionTileRight.getY() * 16, 16, 16);
        screen.drawRect(Color.YELLOW, leftCollisionTileTop.getX() * 16, leftCollisionTileTop.getY() * 16, 16, 16);
        screen.drawRect(Color.YELLOW, leftCollisionTileBottom.getX() * 16, leftCollisionTileBottom.getY() * 16, 16, 16);
        screen.drawRect(Color.PINK, rightCollisionTileTop.getX() * 16, rightCollisionTileTop.getY() * 16, 16, 16);
        screen.drawRect(Color.PINK, rightCollisionTileBottom.getX() * 16, rightCollisionTileBottom.getY() * 16, 16, 16);
    }

    public void doTileCollisions(Tile[][] collisionTiles) {
        this.collisionTiles = collisionTiles;

        int collisionBoxWidth = collisionTiles.length - 1;
        int collisionBoxHeight = collisionTiles[0].length - 1;

        Tile bottomCollisionTileLeft = collisionTiles[collisionBoxWidth / 2][collisionBoxHeight];
        Tile bottomCollisionTileRight = collisionTiles[(collisionBoxWidth * 16 + 16) / 16 / 2][collisionBoxHeight];

        Tile topCollisionTileLeft = collisionTiles[collisionBoxWidth / 2][0];
        Tile topCollisionTileRight = collisionTiles[(collisionBoxWidth * 16 + 16) / 16 / 2][0];

        Tile leftCollisionTileTop = collisionTiles[0][collisionBoxHeight / 2];
        Tile leftCollisionTileBottom = collisionTiles[0][(collisionBoxHeight * 16 + 16) / 16 / 2];

        Tile rightCollisionTileTop = collisionTiles[collisionBoxWidth][collisionBoxHeight / 2];
        Tile rightCollisionTileBottom = collisionTiles[collisionBoxWidth][(collisionBoxHeight * 16 + 16) / 16 / 2];

        boolean leftCollision = xDir < 0 && isSolid(leftCollisionTileTop, leftCollisionTileBottom);
        boolean rightCollision = xDir > 0 && isSolid(rightCollisionTileTop, rightCollisionTileBottom);
        boolean topCollision = yDir < 0 && isSolid(topCollisionTileLeft, topCollisionTileRight);
        boolean bottomCollision = !onGround && isSolid(bottomCollisionTileLeft, bottomCollisionTileRight);
        boolean falling = !isSolid(bottomCollisionTileLeft, bottomCollisionTileRight);

        System.out.println("yDir: " + yDir + ", Left: " + leftCollision + ", Right: " + rightCollision + ", Top: " + topCollision + ", Bottom: " + bottomCollision + ", Falling: " + falling);

        if (falling) {
            onGround = false;
        }

        if (bottomCollision) {
            onGround = true;
            yDir = 0;
            y = bottomCollisionTileLeft.getY() * 16 - height * 16;
        }

        if (topCollision) {
            yDir = 0;
            y = topCollisionTileLeft.getY() * 16 + 16;
        }

        if (leftCollision && !bottomCollision && !topCollision) {
            xDir = 0;
            x = leftCollisionTileBottom.getX() * 16 + 16;
        }

        if (rightCollision && !bottomCollision && !topCollision) {
            xDir = 0;
            x = rightCollisionTileBottom.getX() * 16 - 16;
        }
    }

    private boolean isSolid(Tile tile1, Tile tile2) {
        return (tile1.isSolid() || tile2.isSolid());
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getxDir() { return xDir; }
    public float getyDir() { return yDir; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
