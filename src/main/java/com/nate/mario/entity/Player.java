package com.nate.mario.entity;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.nate.mario.gfx.EntitySprite;
import com.nate.mario.item.CoinItem;
import com.nate.mario.item.Item;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.ItemBlockTile;
import com.nate.mario.level.tile.Tile;

public class Player extends Entity {

    private static final int STARTING_SIZE = 2;
    private static final String STARTING_SPRITE = EntitySprite.MARIO_TALL_STILL;

    private static final float HOR_DECEL_RATE = 0.1f;
    private static final float HOR_ACCEL_RATE = 0.08f;
    private static final float HOR_MAX_SPEED = 3.0f;

    private static final float VER_DECEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 7.0f;

    private static final float SKID_RATE = 1.4f;
    
    private static final float[] JUMP_ACCEL_RATE = {
        -3.9f,
        -3.9f,
        -3.9f,
        -3.9f,
        -3.9f,
        -3.5f,
        -3.5f,
        -3.5f,
        -3.5f,
        -3.5f,
        -3.5f,
        -2.5f,
        -2.5f,
        -2.5f,
        -2.5f,
        -2.5f,
        -1.5f,
        -1.5f,
        -1.5f,
        -1.5f,
        -1.5f,
        -1.5f,
        -1.5f,
        -1.5f,
        -0.5f,
        -0.5f,
        -0.5f
    };

    private final int rightKey = KeyEvent.VK_D;
    private final int leftKey = KeyEvent.VK_A;
    // private final int crouchKey = KeyEvent.VK_S;
    private final int jumpKey = KeyEvent.VK_SLASH;
    private final int jumpKey2 = KeyEvent.VK_SPACE;
    // private final int actionKey = KeyEvent.VK_PERIOD;

    private boolean hasJumped = false;
    
    private int coinCount = 0;
    private int maxX;

    public Player(float xTile, float yTile) {
        super(xTile, yTile, 0, 0, 1, STARTING_SIZE, STARTING_SPRITE);
        maxX = (int) xTile * 16;
    }

    @Override
    public void getMovement(boolean[] keys, Level level) {
        if (yDir != 0 && onGround) onGround = false;

        //Falling
        if (!onGround) {
            if (yDir + VER_DECEL_RATE > VER_MAX_SPEED) yDir = VER_MAX_SPEED;
            else yDir += VER_DECEL_RATE;
        }
        
        if ((!keys[leftKey] && !keys[rightKey]) || (keys[leftKey] && keys[rightKey])) {
            if (xDir > 0) {
                if (xDir - HOR_DECEL_RATE < 0) xDir = 0;
                else xDir -= HOR_DECEL_RATE;
            } else if (xDir < 0) {
                if (xDir + HOR_DECEL_RATE > 0) xDir = 0;
                else xDir += HOR_DECEL_RATE;
            }
        } else if (keys[leftKey]) {
            if (xDir > 0) {
                if (xDir - HOR_DECEL_RATE * SKID_RATE < 0) {
                    xDir = 0;
                } else {
                    if (hasJumped) xDir -= HOR_DECEL_RATE / 3;
                    else xDir -= HOR_DECEL_RATE * SKID_RATE;
                }
            } else if (xDir - HOR_ACCEL_RATE < -HOR_MAX_SPEED) {
                xDir = -HOR_MAX_SPEED;
            } else {
                if (hasJumped && !onGround) xDir -= HOR_ACCEL_RATE / 3;
                else xDir -= HOR_ACCEL_RATE;
            }
        } else if (keys[rightKey]) {
            if (xDir < 0) {
                if (xDir + HOR_DECEL_RATE * SKID_RATE > 0) {
                    xDir = 0;
                } else {
                    if (hasJumped) xDir += HOR_DECEL_RATE / 3;
                    else xDir += HOR_DECEL_RATE * SKID_RATE;
                }
            } else if (xDir + HOR_ACCEL_RATE > HOR_MAX_SPEED) {
                xDir = HOR_MAX_SPEED;
            } else {
                if (hasJumped && !onGround) xDir += HOR_ACCEL_RATE / 3;
                else xDir += HOR_ACCEL_RATE;
            }
        }

        //Only update the maxX variable if the player has advanced further to the right, and the player hasn't
        //reached the right edge of the screen. Without this the ability of the player to retreat to the left
        //would be prevented once the camera stops scrolling to the right at the edge of the level
        if (x > maxX && x < level.getTiles().length * 16 - 190) {
            maxX = (int) x;
        }

        //Prevent player from retreating backwards
        if (x + xDir < maxX - 96) {
            x = maxX - 96;
            xDir = 0;
        }

        //Prevent player from clipping past right side of level
        if (x + xDir >= level.getTiles().length * 16 - 48) {
            x = level.getTiles().length * 16 - 48;
            xDir = 0;
        }

        //Prevent player from clipping past left side of level
        if (x + xDir < 32) {
            x = 32;
            xDir = 0;
        }

        if (keys[jumpKey] || keys[jumpKey2]) {
            if (jumpTick == 0 && onGround && !hasJumped) {
                hasJumped = true;
                yDir = JUMP_ACCEL_RATE[jumpTick++];
            } else if (jumpTick > 0 && jumpTick < JUMP_ACCEL_RATE.length) {
                yDir = JUMP_ACCEL_RATE[jumpTick++];
            } else if (jumpTick == JUMP_ACCEL_RATE.length) {
                jumpTick = 0;
            }
        }

        if (!keys[jumpKey] && !keys[jumpKey2]) {
            jumpTick = 0;
            if (onGround && hasJumped) hasJumped = false;
        }
    }

    public void doItemCollisions(List<Item> items) {
        for (Item item : items) {
            Rectangle playerRect = new Rectangle((int) x, (int) y, width, height);
            Rectangle itemRect = new Rectangle(item.getX(), item.getY(), 16, 16);
            
            if (playerRect.intersects(itemRect)) {
                if (item instanceof CoinItem) {
                    increaseCoinCount();
                    item.toBeDeleted();
                }
            }
        }
    }

    public void topTileCollide(Tile tile) {
        if (tile instanceof ItemBlockTile) {
            System.out.println("Collided with item block tile from below");
            ItemBlockTile itemBlockTile = (ItemBlockTile) tile;
            itemBlockTile.toBeDeleted();
        }
    }

    private void increaseCoinCount() {
        coinCount++;
    }

    public void setHeight(int tiles) {
        height = tiles * EntitySprite.TILE_HEIGHT;
    }

    public int getCoinCount() { return coinCount; }
}
