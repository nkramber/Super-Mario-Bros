package com.nate.mario.entity.player;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.nate.mario.entity.Entity;
import com.nate.mario.gfx.EntitySprite;
import com.nate.mario.item.CoinItem;
import com.nate.mario.item.FireFlowerItem;
import com.nate.mario.item.Item;
import com.nate.mario.item.MushroomItem;
import com.nate.mario.item.PowerUpItem;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.ItemBlockTile;
import com.nate.mario.level.tile.Tile;

public class Player extends Entity {

    private static final float WALK_DECEL_RATE = 0.1f;
    private static final float WALK_ACCEL_RATE = 0.03f;
    private static final float WALK_MAX_SPEED = 1.5f;
    private static final float SPRINT_DECEL_RATE = 0.1f;
    private static final float SPRINT_ACCEL_RATE = 0.05f;
    private static final float SPRINT_MAX_SPEED = 2.5f;
    private static final float VER_ACCEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 7.0f;
    private static final float SKID_RATE = 1.3f;
    private static final float[] JUMP_SPEED_RATE = {
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
    private final int actionKey = KeyEvent.VK_PERIOD;
    private final int actionKey2 = KeyEvent.VK_SHIFT;

    private float currentHorAccelRate = WALK_ACCEL_RATE;
    private float currentHorDecelRate = WALK_DECEL_RATE;
    private float currentHorMaxSpeed = WALK_MAX_SPEED;

    private PowerUpState powerUpState = PowerUpState.SMALL;
    private boolean powerUpStateChanged = false;
    private boolean hasJumped = false;
    private boolean sprinting = false;
    private boolean skidding = false;
    
    private int score = 0;
    private int coinCount = 0;
    private int maxX;

    private float oldX, oldY;

    public Player(int xTile, int yTile, int score, EntitySprite sprite) {
        super(xTile, yTile, 0, 0, 1, sprite);
        this.score = score;
        maxX = (int) xTile * 16;
        facingLeft = false;
        oldX = (int) xTile * 16;
        oldY = (int) yTile * 16;
    }

    public void getMovement(boolean[] keys, Level level) {
        skidding = false;
        if (yDir != 0 && onGround) onGround = false;

        if ((keys[actionKey] || keys[actionKey2]) && !sprinting) {
            sprinting = true;
            currentHorAccelRate = SPRINT_ACCEL_RATE;
            currentHorDecelRate = SPRINT_DECEL_RATE;
            currentHorMaxSpeed = SPRINT_MAX_SPEED;
        } else if ((!keys[actionKey] && !keys[actionKey2]) && sprinting) {
            sprinting = false;
            currentHorAccelRate = WALK_ACCEL_RATE;
            currentHorDecelRate = WALK_DECEL_RATE;
            currentHorMaxSpeed = WALK_MAX_SPEED;
        }

        //Falling
        if (!onGround) {
            if (yDir + VER_ACCEL_RATE > VER_MAX_SPEED) yDir = VER_MAX_SPEED;
            else yDir += VER_ACCEL_RATE;
        }
        
        //Either both sideways keys are being held or neither of them are, so slow down
        if ((!keys[leftKey] && !keys[rightKey]) || (keys[leftKey] && keys[rightKey])) {
            if (xDir > 0) {
                if (xDir - currentHorDecelRate < 0) xDir = 0;
                else xDir -= currentHorDecelRate;
            } else if (xDir < 0) {
                if (xDir + currentHorDecelRate > 0) xDir = 0;
                else xDir += currentHorDecelRate;
            }
        } else if (keys[leftKey]) {
            if (onGround) facingLeft = true;
            if (xDir > 0) { //If we're moving to the right when we press left, we 'skid'
                if (xDir - currentHorDecelRate * SKID_RATE < 0) { //Done skidding
                    xDir = 0;
                } else {
                    if ((hasJumped && !onGround) || (falling && !onGround)) xDir -= currentHorDecelRate / 3; //If we're in the air, don't skid, instead only reduce our momentum by only a small amount
                    else {
                        skidding = true;
                        xDir -= currentHorDecelRate * SKID_RATE;
                    }
                }
            } else if (xDir - currentHorAccelRate < -currentHorMaxSpeed) { //Reached max speed in the left direction
                if (!sprinting) xDir += currentHorDecelRate;
                else xDir = -currentHorMaxSpeed;
            } else {
                if ((hasJumped && !onGround) || (falling && !onGround)) xDir -= currentHorAccelRate / 3; //If we're in the air, only increase our momentum by a small amount
                else xDir -= currentHorAccelRate;
            }
        } else if (keys[rightKey]) {
            if (onGround) facingLeft = false;
            if (xDir < 0) { //If we're moving to the left when we press right, we 'skid'
                if (xDir + currentHorDecelRate * SKID_RATE > 0) { //Done skidding
                    xDir = 0;
                } else {
                    if ((hasJumped && !onGround) || (falling && !onGround)) xDir += currentHorDecelRate / 3; //If we're in the air, don't skid
                    else {
                        skidding = true;
                        xDir += currentHorDecelRate * SKID_RATE;
                    }
                }
            } else if (xDir + currentHorAccelRate > currentHorMaxSpeed) { //Reached max speed in the right direction
                if (!sprinting) xDir -= currentHorDecelRate;
                else xDir = currentHorMaxSpeed;
            } else {
                if ((hasJumped && !onGround) || (falling && !onGround)) xDir += currentHorAccelRate / 3; //If we're in the air, only increase our momentum by a small amount
                else xDir += currentHorAccelRate;
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
                yDir = JUMP_SPEED_RATE[jumpTick++];
            } else if (jumpTick > 0 && jumpTick < JUMP_SPEED_RATE.length) {
                yDir = JUMP_SPEED_RATE[jumpTick++];
            } else if (jumpTick == JUMP_SPEED_RATE.length) {
                jumpTick = 0;
            }
        }

        if (!keys[jumpKey] && !keys[jumpKey2]) {
            jumpTick = 0;
            if (onGround && hasJumped) hasJumped = false;
        }
    }

    @Override
    public void move() {
        oldX = x;
        oldY = y;
        x += xDir;
        y += yDir;
    }

    public void doItemCollisions(List<Item> items) {
        powerUpStateChanged = false;
        for (Item item : items) {
            int itemX = (int) item.getX();
            int itemY = (int) item.getY();

            Rectangle playerRect = new Rectangle((int) x, (int) y, width, height);
            Rectangle itemRect = new Rectangle(itemX, itemY, 12, 16);

            if (item instanceof PowerUpItem) {
                if (((PowerUpItem)item).inSpawnAnimation()) itemRect = new Rectangle(itemX, itemY, 12, 12);
            }
            
            if (playerRect.intersects(itemRect)) {
                item.setToBeDeleted();
                if (item instanceof CoinItem) {
                    increaseCoinCount();
                    addToScore(200);
                } else if (item instanceof MushroomItem) {
                    changePowerUpState(PowerUpState.BIG);
                    addToScore(1000);
                } else if (item instanceof FireFlowerItem) {
                    if (EntitySprite.MARIO_BIG.contains(currentSprite)) changePowerUpState(PowerUpState.FIRE);
                    else changePowerUpState(PowerUpState.BIG);
                    addToScore(1000);
                }
            }
        }
    }

    private void changePowerUpState(PowerUpState state) {
        if (powerUpState.equals(state)) return;

        powerUpStateChanged = true;

        if (state.equals(PowerUpState.BIG)) {
            y -= 16;
            setHeight(2);
        } else if (state.equals(PowerUpState.SMALL)) {
            y += 16;
            setHeight(1);
        }

        powerUpState = state;
    }

    public void topTileCollide(Tile tile) {
        if (tile instanceof ItemBlockTile) {
            ItemBlockTile itemBlockTile = (ItemBlockTile) tile;
            itemBlockTile.toBeDeleted();

            if (itemBlockTile.containsItem()) itemBlockTile.createItem();
        }
    }

    int runSprite = 0;
    long time = 0;

    @Override
    public void updateAnimation() {
        if (time == 0) time = System.currentTimeMillis();

        if (powerUpStateChanged) {
            int spriteIndex;
            if (powerUpState.equals(PowerUpState.BIG)) {
                spriteIndex = EntitySprite.MARIO_SMALL.indexOf(currentSprite);
                currentSprite = EntitySprite.MARIO_BIG.get(spriteIndex);
            } else if (powerUpState.equals(PowerUpState.FIRE)) {
                spriteIndex = EntitySprite.MARIO_BIG.indexOf(currentSprite);
                currentSprite = EntitySprite.MARIO_FIRE.get(spriteIndex);
            } else if (powerUpState.equals(PowerUpState.SMALL)) {
                if (EntitySprite.MARIO_BIG.contains(currentSprite)) {
                    spriteIndex = EntitySprite.MARIO_BIG.indexOf(currentSprite);
                    currentSprite = EntitySprite.MARIO_SMALL.get(spriteIndex);
                } else {
                    spriteIndex = EntitySprite.MARIO_FIRE.indexOf(currentSprite);
                    currentSprite = EntitySprite.MARIO_SMALL.get(spriteIndex);
                }
            }
        }

        if (Math.abs(oldX - x) < 0.21f && Math.abs(oldY - y) < 0.21f && onGround) {
            time = 0;
            if (powerUpState.equals(PowerUpState.SMALL)) currentSprite = EntitySprite.MARIO_SMALL_STILL;
            else if (powerUpState.equals(PowerUpState.BIG)) currentSprite = EntitySprite.MARIO_BIG_STILL;
            else if (powerUpState.equals(PowerUpState.FIRE)) currentSprite = EntitySprite.MARIO_FIRE_STILL;
            runSprite = 0;
        } else if (hasJumped && !falling) {
            time = 0;
            if (powerUpState.equals(PowerUpState.SMALL)) currentSprite = EntitySprite.MARIO_SMALL_JUMP;
            else if (powerUpState.equals(PowerUpState.BIG)) currentSprite = EntitySprite.MARIO_BIG_JUMP;
            else if (powerUpState.equals(PowerUpState.FIRE)) currentSprite = EntitySprite.MARIO_FIRE_JUMP;
        } else if (skidding) {
            time = 0;
            if (powerUpState.equals(PowerUpState.SMALL)) currentSprite = EntitySprite.MARIO_SMALL_TURN;
            else if (powerUpState.equals(PowerUpState.BIG)) currentSprite = EntitySprite.MARIO_BIG_TURN;
            else if (powerUpState.equals(PowerUpState.FIRE)) currentSprite = EntitySprite.MARIO_FIRE_TURN;
        } else if (falling) {
            time = 0;
            return;
        } else {
            long currentTime = System.currentTimeMillis();
            int spriteCycleTime;

            if (sprinting) {
                if (currentHorMaxSpeed - Math.abs(xDir) > 0.15f) {
                    spriteCycleTime = 80;
                } else {
                    spriteCycleTime = 30;
                }
            } else {
                if (currentHorMaxSpeed - Math.abs(xDir) > 0.15f) {
                    spriteCycleTime = 120;
                } else {
                    spriteCycleTime = 50;
                }
            }

            if (currentTime - time > spriteCycleTime) {
                time += spriteCycleTime;
                if (runSprite < 2) runSprite++;
                else runSprite = 0;
            }

            if (powerUpState.equals(PowerUpState.SMALL)) currentSprite = EntitySprite.MARIO_SMALL_RUN[runSprite];
            else if (powerUpState.equals(PowerUpState.BIG)) currentSprite = EntitySprite.MARIO_BIG_RUN[runSprite];
            else if (powerUpState.equals(PowerUpState.FIRE)) currentSprite = EntitySprite.MARIO_FIRE_RUN[runSprite];
        }
    }

    private void increaseCoinCount() {
        coinCount++;
    }

    private void addToScore(int amountToAdd) {
        score = score + amountToAdd;
    }

    public void setHeight(int tiles) {
        height = tiles * EntitySprite.TILE_HEIGHT;
    }

    public int getCoinCount() { return coinCount; }
    public int getScore() { return score; }
}
