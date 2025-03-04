package com.nate.mario.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nate.mario.entity.Entity;
import com.nate.mario.entity.player.Player;
import com.nate.mario.entity.player.PowerUpState;
import com.nate.mario.gfx.Screen;
import com.nate.mario.item.BlockCoin;
import com.nate.mario.item.CoinItem;
import com.nate.mario.item.FireFlowerItem;
import com.nate.mario.item.Item;
import com.nate.mario.item.MushroomItem;
import com.nate.mario.item.PowerUpItem;
import com.nate.mario.level.tile.EmptyItemBlockTile;
import com.nate.mario.level.tile.GroundTile;
import com.nate.mario.level.tile.ItemBlockTile;
import com.nate.mario.level.tile.SkyTile;
import com.nate.mario.level.tile.Tile;

public class Level {

    private static final int TIME_TICK_INTERVAL = 400; //Time in MS between in-game time decrementing
    private final int deathHeight; //Y coordinate where anything below this on screen (greater than this by value) triggers the death of the entity
    private final int deathHeightWhenAnimating; //Used when we are in the death animation - below this (greater than by value) triggers the level reset

    //Data for and from loading the level
    private BufferedImage levelImage;
    private String levelName;
    private Color levelType;

    //Data storage
    private Tile[][] tiles;
    private List<Tile> tilesToTick;
    private List<Tile> onScreenTiles;
    private List<Item> items;
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
        tilesToTick = new ArrayList<>();
        onScreenTiles = new ArrayList<>();
        items = new ArrayList<>();
        entities = new ArrayList<>();
        onScreenEntities = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color tileData = new Color(levelImage.getRGB(x, y));

                //Top left corner corresponds to our level type, should be the sky tile color
                if (x == 0 && y == 0) {
                    if (tileData.getRGB() == LevelType.OVERWORLD.getRGB()) levelType = LevelType.OVERWORLD;
                    tiles[x][y] = new GroundTile(x, y);
                    continue;
                //Below level type is the starting level time, red + green = time units
                } else if (x == 0 && y == 1) {
                    timeRemaining = (tileData.getRed() + tileData.getGreen());
                    tiles[x][y] = new GroundTile(x, y);
                    continue;
                //Below starting level time is player spawn position
                } else if (x == 0 && y == 2) {
                    playerSpawnX = tileData.getRed();
                    playerSpawnY = tileData.getGreen();
                    tiles[x][y] = new GroundTile(x, y);
                    continue;
                }

                //Red pixel data = tile
                int id = tileData.getRed();
                tiles[x][y] = Tile.tiles.get(id).newTile(x, y);
                if (tiles[x][y].isTickable()) tilesToTick.add(tiles[x][y]);

                //Blue pixel data = entity
                if (tileData.getBlue() != 0) {
                    id = tileData.getBlue();
                    entities.add(Entity.entities.get(id).newEntity(x, y));
                }

                //Green pixel data = item
                if (tileData.getGreen() != 0) {
                    id = tileData.getGreen();
                    //Add BlockTile (1UP, big coin) item adding here
                    if (tiles[x][y] instanceof ItemBlockTile) {
                        if (Item.items.containsKey(id)) ((ItemBlockTile) tiles[x][y]).addItemToItemBlock(Item.items.get(id));
                        else items.add(new BlockCoin(x * 16, y * 16));
                    }
                }
            }
        }

        //Set our deathHeight variables based on the level height. This determines at what Y coordinate to kill entities or remove items
        deathHeight = tiles[0].length * 16 - 48;
        //Death height is lower when the player is animating. This keep correct death animation timing
        deathHeightWhenAnimating = deathHeight + 402;
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
        //Keep track of which entities need to be removed - can't remove inside our loop, so we remove after
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            if (onScreenEntities.contains(entity)) entity.tick(this);
            if (entity.isToBeDeleted() || entity.getY() > deathHeight) entitiesToRemove.add(entity);
        }

        for (Entity entity : entitiesToRemove) entities.remove(entity); //add death animation to goombas
    }

    private void tickItems() {
        //Keep track of which items need to be removed - can't remove inside our loop, so we remove after
        List<Item> itemsToRemove = new ArrayList<>();
        for (Item item : items) {
            item.tick(this);
            if (item.isToBeDeleted()) itemsToRemove.add(item);
        }

        for (Item item : itemsToRemove) items.remove(item);
    }

    private void tickTiles() {
        //Keep track of which tiles we are going to add and remove from our tick list
        List<Tile> tilesToRemoveFromTickList = new ArrayList<>();
        List<Tile> tilesToAddToTickList = new ArrayList<>();
        for (Tile tile : tilesToTick) {
            if (onScreenTiles.contains(tile)) tile.tick(this);
            if (tile.isToBeDeleted()) {
                tilesToRemoveFromTickList.add(tile);
                //If the tile is an ItemBlockTile, we want to replace it with a new empty item block. Otherwise we simply remove it from the tick list
                if (tile instanceof ItemBlockTile) {
                    EmptyItemBlockTile newTile = new EmptyItemBlockTile(tile.getxTile(), tile.getyTile());
                    tiles[tile.getxTile()][tile.getyTile()] = newTile;
                    tilesToAddToTickList.add(newTile);
                }
            }
        }

        //Add and remove tiles from our tick list
        for (Tile tile : tilesToRemoveFromTickList) tilesToTick.remove(tile);
        for (Tile tile : tilesToAddToTickList) tilesToTick.add(tile);
    }

    public void createItem(Item item, int xTile, int yTile) {
        //If it is a PowerUpItem, evaluate Mario's current PowerUpState and spawn a mushroom if we're small
        if (item instanceof PowerUpItem) {
            if (player.getPowerUpState(PowerUpState.SMALL)) items.add(new MushroomItem(xTile * 16, yTile * 16));
            else items.add(new FireFlowerItem(xTile * 16, yTile * 16));
        }

        //If we aren't spawning a power up item, spawn an animated coin above the block and give a coin to the player
        else {
            items.add(new BlockCoin(xTile * 16, yTile * 16 - 16));
            player.increaseCoinCount();
            player.addToScore(CoinItem.SCORE);
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
                if (powerUpItem.inSpawnAnimation()) screen.drawTile("sky_tile", (int) powerUpItem.getX(), powerUpItem.getInitialY());
            }
        }

        //Render tiles
        onScreenTiles.clear();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                Tile tile = tiles[x][y];
                if (!screen.isOffScreen(x * 16, 0)) {
                    onScreenTiles.add(tile);
                    if (tile instanceof EmptyItemBlockTile && ((EmptyItemBlockTile)tile).isAnimating()) {
                        //Subtract the animation frame from the Y coordinate
                        screen.drawTile(tile.getName(), x * 16, y * 16 - ((EmptyItemBlockTile)tile).getAnimationFrame());
                    }
                    //Don't draw sky tiles or they'll cover up our items
                    else if (!(tile instanceof SkyTile)) screen.drawTile(tile.getName(), x * 16, y * 16);
                }
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
    public Player getPlayer() { return player; }
    public boolean isLevelFinished() { return levelFinished; }
    public boolean isGameOver() { return gameOver; }
    public boolean resetLevel() { return resetLevel; }
    public String getLevelName() { return levelName; }
    public int getPlayerSpawnX() { return playerSpawnX; }
    public int getPlayerSpawnY() { return playerSpawnY; }
    public int getDeathHeightWhenAnimating() { return deathHeightWhenAnimating; }
    public int getDeathHeight() { return deathHeight; }
    public int getTimeRemaining() { return timeRemaining; }
    public BufferedImage getLevelImage() { return levelImage; }
}
