package com.nate.mario.entity.player;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;

import com.nate.mario.entity.Entity;
import com.nate.mario.entity.Goomba;
import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.sprite.PlayerSprite;
import com.nate.mario.item.CoinItem;
import com.nate.mario.item.FireFlowerItem;
import com.nate.mario.item.Item;
import com.nate.mario.item.MushroomItem;
import com.nate.mario.item.PowerUpItem;
import com.nate.mario.level.Level;
import com.nate.mario.level.tile.ItemBlockTile;
import com.nate.mario.level.tile.Tile;
import com.nate.mario.util.Collision;
import com.nate.mario.util.Timer;

public class Player extends Entity {

    private static final int DEATH_TIMER_TIME = 750; //time in ms before we begin our death animation
    private static final int INVINCIBLE_TIMER_TIME = 2000;
    private static final float WALK_DECEL_RATE = 0.06f;
    private static final float WALK_ACCEL_RATE = 0.035f;
    private static final float WALK_MAX_SPEED = 1.5f;
    private static final float SPRINT_DECEL_RATE = 0.1f;
    private static final float SPRINT_ACCEL_RATE = 0.05f;
    private static final float SPRINT_MAX_SPEED = 2.5f;
    public static final float VER_ACCEL_RATE = 0.35f;
    private static final float VER_MAX_SPEED = 7.0f;
    private static final float SKID_RATE = 1.3f;
    private static final float MONSTER_JUMPED_ON_SPEED = -3.2f;
    private static final float[] JUMP_SPEED_RATE = {
        -4.1f,
        -4.1f,
        -4.1f,
        -4.1f,
        -4.1f,
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

    private int invincibleFrame = 0;
    private int animationFrame = 0;
    private int runSpriteFrame = 0;
    private long time = 0;

    private Timer deathTimer = null;
    private Timer invincibleTimer = null;
    private PowerUpState powerUpState = PowerUpState.SMALL;

    private boolean isDead;
    private boolean invincible;
    private boolean skipRender;
    private boolean inDyingAnimation;
    private boolean hasJumped;
    private boolean sprinting;
    private boolean skidding;
    private boolean growing;
    private boolean shrinking;
    private boolean gainedFireFlower;
    
    private int score = 0;
    private int coinCount = 0;
    private int maxX;

    public Player(int xTile, int yTile, int score) {
        super(xTile, yTile, 0, 0, PlayerSprite.MARIO_SMALL_STILL);
        this.score = score;
        maxX = (int) xTile * 16;
        facingLeft = false;
    }

    public boolean tick(Level level, boolean[] keys) {
        //Update our death animation if necessary
        if (inDyingAnimation) {
            //Still above the height threshold where we reset the level
            if (y <= level.getDyingAnimationHeight()) {
                updateAnimation();

                if (deathTimer != null) tickDeathTimer();
                else doGravity();

                move();
            } else level.doResetLevel();
            //Skip the rest of the tick if we're dead
            return true;
        }

        //Check if we should die or are already flagged as dead
        if (level.getTimeRemaining() <= 0 || isDead || y > level.getDeathHeight()) {
            xDir = 0;
            yDir = 0;
            onGround = false;
            setInDyingAnimation();
            return true;
        }

        if (isDoingPowerUpAnimation()) {
            updateAnimation();
            return true;
        }

        getMovement(level, keys);
        doTileCollisions(Collision.getEntityCollisionTiles(this, level.getTiles()));
        doEntityCollisions(level.getEntities());

        if (invincible) tickInvincibleTimer();

        move();

        List<Item> collisionItems = Collision.getEntityCollisionItems(this, level.getItems());
        doItemCollisions(collisionItems);
        
        updateAnimation();

        return false;
    }

    public void getMovement(Level level, boolean[] keys) {
        skidding = false;
        if (yDir != 0 && onGround) onGround = false;

        //This mean we're falling
        if (!onGround) doGravity();
        //Skip movement calculation if we're dead
        if (inDyingAnimation) return;

        //Check if our sprint state has changed since the last tick. If we were sprinting but are not pressing the action key, "un-sprint" the player and vice-versa
        if ((keys[actionKey] || keys[actionKey2]) && !sprinting) isSprinting();
        else if ((!keys[actionKey] && !keys[actionKey2]) && sprinting) isWalking();
        
        //Either both sideways keys are being held or neither of them are, so slow down
        if ((!keys[leftKey] && !keys[rightKey]) || (keys[leftKey] && keys[rightKey])) horizontalDeceleration();

        else if (keys[leftKey]) moveLeft();
        else if (keys[rightKey]) moveRight();

        //Only update the maxX variable if the player has advanced further to the right, and the player hasn't
        //reached the right edge of the screen. Without this the ability of the player to retreat to the left
        //would be prevented once the camera stops scrolling to the right at the edge of the level
        if (x > maxX && x < level.getTiles().length * 16 - 190) maxX = (int) x;

        //Prevent player from retreating backwards
        if (x + xDir < maxX - 96) {
            x = maxX - 96;
            xDir = 0;
        }

        //Prevent the player from clipping into the invisible tiles on the far left and far right of the level
        preventClippingOutsideLevel(level.getTiles().length);

        //Jump
        if (keys[jumpKey] || keys[jumpKey2]) doJump();

        //Stop jumping
        if (!keys[jumpKey] && !keys[jumpKey2]) {
            jumpTick = 0;
            if (onGround && hasJumped) hasJumped = false;
        }
    }

    @Override
    public void move() {
        x += xDir;
        y += yDir;
    }

    public void doGravity() {
        if (yDir + VER_ACCEL_RATE > VER_MAX_SPEED) yDir = VER_MAX_SPEED;
        else yDir += VER_ACCEL_RATE;
    }

    private void horizontalDeceleration() {
        if (xDir > 0) {
            if (xDir - currentHorDecelRate < 0) xDir = 0;
            else xDir -= currentHorDecelRate;
        } else if (xDir < 0) {
            if (xDir + currentHorDecelRate > 0) xDir = 0;
            else xDir += currentHorDecelRate;
        }
    }

    private void moveLeft() {
        if (onGround) facingLeft = true;
        if (xDir > 0) { //If we're moving to the right when we press left, we 'skid'
            if (xDir - currentHorDecelRate * SKID_RATE < 0) { //Done skidding
                xDir = 0;
            } else {
                if ((hasJumped && !onGround) || (falling && !onGround)) xDir -= currentHorDecelRate; //If we're in the air, don't skid
                else {
                    skidding = true;
                    xDir -= currentHorDecelRate * SKID_RATE;
                }
            }
        } else if (xDir - currentHorAccelRate < -currentHorMaxSpeed) { //Reached max speed in the left direction
            if (!sprinting) xDir += currentHorDecelRate;
            else xDir = -currentHorMaxSpeed;
        } else xDir -= currentHorAccelRate;
    }

    private void moveRight() {
        if (onGround) facingLeft = false;
        if (xDir < 0) { //If we're moving to the left when we press right, we 'skid'
            if (xDir + currentHorDecelRate * SKID_RATE > 0) { //Done skidding
                xDir = 0;
            } else {
                if ((hasJumped && !onGround) || (falling && !onGround)) xDir += currentHorDecelRate; //If we're in the air, don't skid
                else {
                    skidding = true;
                    xDir += currentHorDecelRate * SKID_RATE;
                }
            }
        } else if (xDir + currentHorAccelRate > currentHorMaxSpeed) { //Reached max speed in the right direction
            if (!sprinting) xDir -= currentHorDecelRate;
            else xDir = currentHorMaxSpeed;
        } else xDir += currentHorAccelRate;
    }

    private void isSprinting() {
        sprinting = true;
        currentHorAccelRate = SPRINT_ACCEL_RATE;
        currentHorDecelRate = SPRINT_DECEL_RATE;
        currentHorMaxSpeed = SPRINT_MAX_SPEED;
    }

    private void isWalking() {
        sprinting = false;
        currentHorAccelRate = WALK_ACCEL_RATE;
        currentHorDecelRate = WALK_DECEL_RATE;
        currentHorMaxSpeed = WALK_MAX_SPEED;
    }

    private void preventClippingOutsideLevel(int mapWidthInTiles) {
        //Prevent player from clipping past right side of level
        if (x + xDir >= mapWidthInTiles * 16 - 48) {
            x = mapWidthInTiles * 16 - 48;
            xDir = 0;
        }

        //Prevent player from clipping past left side of level
        if (x + xDir < 32) {
            x = 32;
            xDir = 0;
        }
    }

    private void doJump() {
        if (falling && !hasJumped) {
            hasJumped = true;
            return;
        }
        
        if (jumpTick == 0 && onGround && !hasJumped) {
            hasJumped = true;
            yDir = JUMP_SPEED_RATE[jumpTick++];
        } else if (jumpTick > 0 && jumpTick < JUMP_SPEED_RATE.length) {
            yDir = JUMP_SPEED_RATE[jumpTick++];
        } else if (jumpTick == JUMP_SPEED_RATE.length) {
            jumpTick = 0;
        }
    }

    @Override
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
                            if (tile instanceof ItemBlockTile) {
                                ItemBlockTile itemBlockTile = (ItemBlockTile) tile;
                                itemBlockTile.toBeDeleted();
                                itemBlockTile.createItem();
                            }
                            newY = tile.getyTile() * 16 + 16 - yOffset;
                            jumpTick = 0;
                        } else { //Collide with tile below
                            newY = tile.getyTile() * 16 - height;
                            onGround = true;
                        }

                        verticalEntityRect = new Rectangle((int) (newX) + xOffset, (int) (newY + yOffset), width - xOffset * 2, height - yOffset);
                    }
    
                    if (horizontalEntityRect.intersects(tileRect)) {
                        xDir = 0;
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

    @Override
    public void doEntityCollisions(List<Entity> entities) {
        int yOffset = 4;
        int xOffset = 2;

        Rectangle localCollisionRectangle = new Rectangle((int) x + xOffset, (int) y + yOffset, width - xOffset * 2, height - yOffset);

        for (Entity entity : entities) {
            Rectangle otherCollisionRectangle = new Rectangle((int) entity.getX(), (int) entity.getY(), entity.getWidth(), entity.getHeight());

            if (localCollisionRectangle.intersects(otherCollisionRectangle)) {
                if (entity instanceof Goomba) {
                    if (y + 2 < entity.getY() && yDir > 0) {
                        yDir = MONSTER_JUMPED_ON_SPEED;
                        entity.setToBeDeleted();
                        addToScore(100); //Add multiplicitave scoring for player still in the air combos
                    } else if (!invincible) {
                        if (powerUpState == PowerUpState.SMALL) {
                            isDead = true;
                        } else {
                            changePowerUpState(PowerUpState.SMALL);
                            setInvincible();
                        }
                    }
                }
            }
        }
    }

    public void doItemCollisions(List<Item> items) {
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
                    addToScore(CoinItem.SCORE);
                } else if (item instanceof MushroomItem) {
                    changePowerUpState(PowerUpState.BIG);
                    addToScore(PowerUpItem.SCORE);
                } else if (item instanceof FireFlowerItem) {
                    if (PlayerSprite.MARIO_BIG.contains(currentSprite)) changePowerUpState(PowerUpState.FIRE);
                    else changePowerUpState(PowerUpState.BIG);
                    addToScore(PowerUpItem.SCORE);
                }
            }
        }
    }

    private void changePowerUpState(PowerUpState state) {
        if (powerUpState.equals(state)) return;

        if (state.equals(PowerUpState.BIG)) {
            growing = true;
            y -= 16;
            setHeight(2);
        } else if (state.equals(PowerUpState.SMALL)) {
            shrinking = true;
        } else if (state.equals(PowerUpState.FIRE)) {
            gainedFireFlower = true;
        }

        powerUpState = state;
    }

    public void updateAnimation() {
        if (time == 0) time = System.currentTimeMillis();

        if (invincible) {
            invincibleFrame++;
            if (invincibleFrame % 2 == 0) skipRender = true;
            else skipRender = false;
        }

        if (inDyingAnimation) {
            currentSprite = PlayerSprite.MARIO_SMALL_DIE;
            return;
        }

        if (growing) {
            animationFrame++;
            currentSprite = PlayerSprite.MARIO_GROW_ANIMATION.get(animationFrame / 5);
            if (animationFrame >= PlayerSprite.MARIO_GROW_ANIMATION.size() * 5 - 1) {
                currentSprite = PlayerSprite.MARIO_BIG_STILL;
                growing = false;
                animationFrame = 0;
            }
            return;
        } else if (shrinking) {
            animationFrame++;
            currentSprite = PlayerSprite.MARIO_SHRINK_ANIMATION.get(animationFrame / 5);
            if (animationFrame >= PlayerSprite.MARIO_SHRINK_ANIMATION.size() * 5 - 1) {
                currentSprite = PlayerSprite.MARIO_SMALL_STILL;
                shrinking = false;
                y += 16;
                setHeight(1);
                animationFrame = 0;
            } else {
                return;
            }
        } else if (gainedFireFlower) {
            animationFrame++;
            currentSprite = PlayerSprite.MARIO_FIRE_ANIMATION.get(animationFrame / 5);
            if (animationFrame >= PlayerSprite.MARIO_FIRE_ANIMATION.size() * 5 - 1) {
                currentSprite = PlayerSprite.MARIO_FIRE_STILL;
                gainedFireFlower = false;
                animationFrame = 0;
            } else {
                return;
            }
        }

        if (xDir < 0.22f && xDir > -0.22f && yDir == 0 && onGround) {
            time = 0;
            if (powerUpState.equals(PowerUpState.SMALL)) currentSprite = PlayerSprite.MARIO_SMALL_STILL;
            else if (powerUpState.equals(PowerUpState.BIG)) currentSprite = PlayerSprite.MARIO_BIG_STILL;
            else if (powerUpState.equals(PowerUpState.FIRE)) currentSprite = PlayerSprite.MARIO_FIRE_STILL;
            runSpriteFrame = 0;
        } else if (hasJumped && !falling && !onGround) {
            time = 0;
            if (powerUpState.equals(PowerUpState.SMALL)) currentSprite = PlayerSprite.MARIO_SMALL_JUMP;
            else if (powerUpState.equals(PowerUpState.BIG)) currentSprite = PlayerSprite.MARIO_BIG_JUMP;
            else if (powerUpState.equals(PowerUpState.FIRE)) currentSprite = PlayerSprite.MARIO_FIRE_JUMP;
        } else if (skidding) {
            time = 0;
            if (powerUpState.equals(PowerUpState.SMALL)) currentSprite = PlayerSprite.MARIO_SMALL_TURN;
            else if (powerUpState.equals(PowerUpState.BIG)) currentSprite = PlayerSprite.MARIO_BIG_TURN;
            else if (powerUpState.equals(PowerUpState.FIRE)) currentSprite = PlayerSprite.MARIO_FIRE_TURN;
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
                if (runSpriteFrame < 2) runSpriteFrame++;
                else runSpriteFrame = 0;
            }

            if (powerUpState.equals(PowerUpState.SMALL)) currentSprite = PlayerSprite.MARIO_SMALL_RUN[runSpriteFrame];
            else if (powerUpState.equals(PowerUpState.BIG)) currentSprite = PlayerSprite.MARIO_BIG_RUN[runSpriteFrame];
            else if (powerUpState.equals(PowerUpState.FIRE)) currentSprite = PlayerSprite.MARIO_FIRE_RUN[runSpriteFrame];
        }
    }

    @Override
    public void render(Screen screen) {
        //If skipRender it means we're invincible and we should skip a frame to present a flickering effect
        if (!skipRender) screen.drawSprite(currentSprite, facingLeft, (int) x, (int) y);
    }

    public void tickInvincibleTimer() {
        invincibleTimer.tick();
        if (invincibleTimer.getElapsedTime() < INVINCIBLE_TIMER_TIME) return;
        invincibleTimer = null;
        invincible = false;
        invincibleFrame = 0;
        skipRender = false;
    }

    public void tickDeathTimer() {
        deathTimer.tick();
        if (deathTimer.getElapsedTime() < DEATH_TIMER_TIME) return;
        deathTimer = null;
        yDir = -5.0f;
    }

    public void increaseCoinCount() { coinCount++; }
    public void addToScore(int amountToAdd) { score = score + amountToAdd; }

    public int getCoinCount() { return coinCount; }
    public int getScore() { return score; }
    public boolean isDoingPowerUpAnimation() { return growing || shrinking || gainedFireFlower; }
    public boolean isDead() { return isDead; }
    public boolean isInDyingAnimation() { return inDyingAnimation; }
    public boolean isInvincible() { return invincible; }
    public Timer getDeathTimer() { return deathTimer; }
    public Timer getInvincibleTimer() { return invincibleTimer; }
    
    public void setNotOnGround() { onGround = false; }
    public void setHeight(int tiles) { height = tiles * PlayerSprite.TILE_HEIGHT; }
    public void setxDir(float xDir) { this.xDir = xDir; }
    public void setyDir(float yDir) { this.yDir = yDir; }
    public void setInDyingAnimation() {
        inDyingAnimation = true;
        deathTimer = new Timer();
    }
    public void setNotInvincible() { invincible = false; }
    public void setInvincible() {
        invincible = true;
        invincibleTimer = new Timer();
    }

    @Override
    public Entity newEntity(int xTile, int yTile) {
        return new Player(xTile, yTile, score);
    }
}
