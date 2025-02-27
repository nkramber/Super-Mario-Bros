package com.nate.mario.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nate.mario.entity.Entity;
import com.nate.mario.entity.player.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.sprite.PlayerSprite;
import com.nate.mario.item.BlockCoin;
import com.nate.mario.item.CoinItem;
import com.nate.mario.item.FireFlowerItem;
import com.nate.mario.item.Item;
import com.nate.mario.item.MushroomItem;
import com.nate.mario.item.PowerUpItem;
import com.nate.mario.level.tile.EmptyItemBlockTile;
import com.nate.mario.level.tile.ItemBlockTile;
import com.nate.mario.level.tile.SkyTile;
import com.nate.mario.level.tile.Tile;

public class Level {

    private static final int TIME_TICK_INTERVAL = 400; //Time in MS between in-game time decrementing
    private final int deathHeight; //Y coordinate where anything below this on screen (greater than this by value) triggers the death of the entity
    private final int dyingAnimationHeight; //Used when we are in the death animation - below this (greater than by value) triggers the level reset

    //Data for and from loading the level
    private BufferedImage levelImage;
    private String levelName;
    private Color levelType;

    //Data storage
    public Tile[][] tiles;
    public List<Item> items;
    private List<Entity> entities;
    private List<Entity> onScreenEntities;

    private Player player;
    private int playerSpawnX, playerSpawnY;
    
    //Track our current level time
    private long timeInMillis;
    private int timeRemaining;

    //Track our current level state
    private boolean resetLevel;
    private boolean levelFinished;
    private boolean gameOver;

    public Level(BufferedImage levelImage, String levelName) {
        this.levelImage = levelImage;
        this.levelName = levelName;

        int width = levelImage.getWidth();
        int height = levelImage.getHeight();
        
        tiles = new Tile[width][height];
        items = new ArrayList<>();
        entities = new ArrayList<>();
        onScreenEntities = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(levelImage.getRGB(x, y));

                //Top left corner corresponds to our level type, should be the sky tile color
                if (x == 0 && y == 0) {
                    if (color.getRGB() == LevelType.OVERWORLD.getRGB()) levelType = LevelType.OVERWORLD;

                    tiles[x][y] = Tile.tiles.get(0);
                    continue;
                }

                //Below level type is the starting level time, red + green = time units
                if (x == 0 && y == 1) {
                    timeRemaining = (color.getRed() + color.getGreen());

                    tiles[x][y] = Tile.tiles.get(0);
                    continue;
                }

                //Below starting level time is player spawn position
                if (x == 0 && y == 2) {
                    playerSpawnX = color.getRed();
                    playerSpawnY = color.getGreen();

                    tiles[x][y] = Tile.tiles.get(0);
                }

                //Red is the tile type
                for (Tile tile : Tile.tiles) {
                    if (color.getRed() == tile.getID()) {
                        tiles[x][y] = tile.newTile(x, y, tile.getID(), tile.getName(), tile.isSolid());
                        break;
                    }
                }

                //Green is the item type
                for (Item item : Item.items.values()) {
                    if (color.getGreen() == item.getID()) {
                        if (item instanceof CoinItem) items.add(item.newItem(x * 16, y * 16));
                        else if (tiles[x][y] instanceof ItemBlockTile) ((ItemBlockTile)tiles[x][y]).addItemToItemBlock(item);
                        break;
                    }
                }

                //Blue is the entity type
                for (Entity entity : Entity.entities.values()) {
                    if (color.getBlue() == entity.getID()) {
                        entities.add(entity.newEntity(x, y));
                    }
                }

                if (tiles[x][y] == null) throw new IllegalArgumentException("Valid tile does not exist at " + x + ", " + y + " on level " + levelName);
            }
        }

        deathHeight = tiles[0].length * 16 - 48;
        dyingAnimationHeight = tiles[0].length * 16 + 450;
    }

    public void tick(boolean[] keys) {
        decrementLevelTime();

        //If the player is dead or doing a power up animation, skip the rest of the tick
        boolean skipRestOfTick = player.tick(this, keys);

        if (!skipRestOfTick) {
            tickEntities();
            tickItems();
            tickTiles();
        }
    }

    private void tickEntities() {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            if (onScreenEntities.contains(entity)) entity.tick(this);
            if (entity.isToBeDeleted() || entity.getY() > deathHeight) entitiesToRemove.add(entity);
        }

        for (Entity entity : entitiesToRemove) entities.remove(entity); //add death animation to goombas
    }

    private void tickItems() {
        List<Item> itemsToRemove = new ArrayList<>();
        for (Item item : items) {
            item.tick(this);

            if (item.isToBeDeleted()) itemsToRemove.add(item);
        }

        for (Item item : itemsToRemove) items.remove(item);
    }

    private void tickTiles() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                Tile tile = tiles[x][y];

                if (tile instanceof EmptyItemBlockTile && tile.isAnimating()) {
                    ((EmptyItemBlockTile) tile).animate();
                }

                if (tile instanceof ItemBlockTile) {
                    ItemBlockTile itemBlockTile = (ItemBlockTile) tile;
                    if (itemBlockTile.readyToCreateItem()) {
                        Item itemToCreate = itemBlockTile.getItemToCreate();
                        if (itemToCreate instanceof PowerUpItem) {
                            if (PlayerSprite.MARIO_SMALL.contains(player.getSprite())) items.add(new MushroomItem(itemBlockTile.getxTile() * 16, itemBlockTile.getyTile() * 16));
                            else items.add(new FireFlowerItem(itemBlockTile.getxTile() * 16, itemBlockTile.getyTile() * 16));
                        }

                        //If we aren't spawning a power up item, spawn an animated coin above the block and give a coin to the player
                        else {
                            items.add(new BlockCoin(itemBlockTile.getxTile() * 16, itemBlockTile.getyTile() * 16 - 16));
                            player.increaseCoinCount();
                            player.addToScore(CoinItem.SCORE);
                        }
                    }

                    if (itemBlockTile.isToBeDeleted()) {
                        tiles[itemBlockTile.getxTile()][itemBlockTile.getyTile()] = new EmptyItemBlockTile(itemBlockTile.getxTile(), itemBlockTile.getyTile(), -1, "emptyitemblock", true);
                    }
                }
            }
        }
    }

    private void decrementLevelTime() {
        //Don't decrement the level time if we are in a transition animation
        if (player.isDoingPowerUpAnimation() || player.isInDyingAnimation()) {
            timeInMillis = System.currentTimeMillis();
            return;
        }

        //If the time hasn't been set yet, set it now
        if (timeInMillis == 0) timeInMillis = System.currentTimeMillis();

        //Calculate how much time has passed. If more than TIME_TICK_INTERVAL, reduce the game time by 1
        long currentTimeInMillis = System.currentTimeMillis();
        if (currentTimeInMillis - timeInMillis >= TIME_TICK_INTERVAL) {
            timeRemaining--;
            timeInMillis += TIME_TICK_INTERVAL;
        }
    }

    public void render(Screen screen) {
        //Update the scroll position of the screen based on the players x coordinate and the length of the level
        screen.setScroll((int) player.getX(), tiles.length);
        //Render the entire screen with the sky color of the level type. This prevents the animating '?' blocks from having black pixels underneath
        screen.setBackgroundColor(levelType);

        //Render items
        for (Item item : items) {
            screen.drawItem(item.getSprite(), (int) item.getX(), (int) item.getY());
            if (item instanceof PowerUpItem) {
                PowerUpItem powerUpItem = (PowerUpItem) item;
                if (screen.isOffScreen((int) powerUpItem.getX(), (int) powerUpItem.getY())) {
                    powerUpItem.setToBeDeleted();
                    continue;
                }

                //Draw a blank sky tile below the block containing our item. This prevents the item from appearing below the animating block
                if (powerUpItem.inSpawnAnimation()) screen.drawTile("sky", (int) powerUpItem.getX(), powerUpItem.getInitialY());
            }
        }

        //Render tiles
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                Tile tile = tiles[x][y];
                if (tile instanceof EmptyItemBlockTile && ((EmptyItemBlockTile)tile).isAnimating()) {
                    //Subtract the animation frame from the Y coordinate
                    screen.drawTile(tile.getName(), x * 16, y * 16 - ((EmptyItemBlockTile)tile).getAnimationFrame());
                }
                //Don't draw sky tiles or they'll cover up our items
                else if (!(tile instanceof SkyTile)) screen.drawTile(tile.getName(), x * 16, y * 16);
            }
        }

        screen.drawHud(player.getCoinCount(), player.getScore(), timeRemaining, levelName);

        //Render entities
        onScreenEntities.clear();
        for (Entity entity : entities) {
            if (!screen.isOffScreen((int) entity.getX(), 0)) {
                onScreenEntities.add(entity);
                entity.render(screen);
            }
        }

        player.render(screen);
    }

    public void doResetLevel() { resetLevel = true; }
    public void setPlayer(Player player) { this.player = player; }
    public Tile[][] getTiles() { return tiles; }
    public List<Entity> getEntities() { return entities; }
    public List<Item> getItems() { return items; }
    public boolean isLevelFinished() { return levelFinished; }
    public boolean isGameOver() { return gameOver; }
    public boolean resetLevel() { return resetLevel; }
    public String getLevelName() { return levelName; }
    public int getPlayerSpawnX() { return playerSpawnX; }
    public int getPlayerSpawnY() { return playerSpawnY; }
    public int getDyingAnimationHeight() { return dyingAnimationHeight; }
    public int getDeathHeight() { return deathHeight; }
    public int getTimeRemaining() { return timeRemaining; }
    public BufferedImage getLevelImage() { return levelImage; }
}
