package com.nate.mario.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nate.mario.entity.Entity;
import com.nate.mario.entity.player.Player;
import com.nate.mario.entity.player.PowerUpState;
import com.nate.mario.gfx.Screen;
import com.nate.mario.item.BlockCoinItem;
import com.nate.mario.item.CoinItem;
import com.nate.mario.item.Item;
import com.nate.mario.item.powerupitem.FireFlowerItem;
import com.nate.mario.item.powerupitem.MushroomItem;
import com.nate.mario.item.powerupitem.PowerUpItem;
import com.nate.mario.level.tile.GroundTile;
import com.nate.mario.level.tile.ItemBlockTile;
import com.nate.mario.level.tile.SkyTile;
import com.nate.mario.level.tile.Tile;
import com.nate.mario.level.tile.animatedtile.AnimatedTile;
import com.nate.mario.level.tile.animatedtile.BreakableTile;
import com.nate.mario.level.tile.animatedtile.EmptyItemBlockTile;
import com.nate.mario.particle.BlockParticleSet;
import com.nate.mario.particle.Particle;

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
    private List<Tile> onScreenTiles;
    private List<Item> items;
    private List<Entity> entities;
    private List<Entity> onScreenEntities;
    private List<Particle> particles;

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

        initializeLists();

        int width = levelImage.getWidth();
        int height = levelImage.getHeight();
        tiles = new Tile[width][height];

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

                //Blue pixel data = entity
                if (tileData.getBlue() != 0) {
                    id = tileData.getBlue();
                    Entity entity = Entity.entities.get(id).newEntity(x, y);
                    entities.add(entity);
                    //If the spawn tile is in the onScreenEntity range at the start of the level, add it to our onScreenEntities list
                    if (x < 20) onScreenEntities.add(entity);
                }

                //Green pixel data = item
                if (tileData.getGreen() != 0) {
                    id = tileData.getGreen();
                    //Add BlockTile (1UP, big coin) item adding here
                    if (tiles[x][y] instanceof ItemBlockTile) {
                        if (Item.items.containsKey(id)) ((ItemBlockTile) tiles[x][y]).addItemToItemBlock(Item.items.get(id));
                        else items.add(new BlockCoinItem(x * 16, y * 16));
                    }
                }
            }
        }

        //Set our deathHeight variables based on the level height. This determines at what Y coordinate to kill entities or remove items
        deathHeight = tiles[0].length * 16 - 64;
        //Death height is lower when the player is animating. This keep correct death animation timing
        deathHeightWhenAnimating = deathHeight + 402;
    }

    private void initializeLists() {
        onScreenTiles = new ArrayList<>();
        items = new ArrayList<>();
        entities = new ArrayList<>();
        onScreenEntities = new ArrayList<>();
        particles = new ArrayList<>();
    }

    public void tick(boolean[] keys) {
        decrementLevelTime();

        //If the player is dead or doing a power up animation, skip the rest of the tick
        player.tick(this, keys);

        if (!player.isInDyingAnimation()) {
            tickParticles();
            tickItems();
            tickTiles();
            if (!player.isDoingPowerUpAnimation()) {
                tickEntities();
            }
        }

        doEntityCollisions();
    }

    private void doEntityCollisions() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e1 = entities.get(i);
            if (onScreenEntities.contains(e1)) {
                if (player.getCollisionRect().intersects(e1.getCollisionRect()) && e1.isCollidable()) {
                    e1.entityCollide(player);
                    player.entityCollide(e1);
                }
                for (int j = i + 1; j < entities.size(); j++) {
                    Entity e2 = entities.get(j);
                    if (onScreenEntities.contains(e2)) {
                        if (e1.getCollisionRect().intersects(e2.getCollisionRect())) {
                            e1.entityCollide(e2);
                            e2.entityCollide(e1);
                        }
                    }
                }
            }
        }
    }

    private void tickParticles() {
        List<Particle> particlesToRemove = new ArrayList<>();
        for (Particle particle : particles) {
            if (particle.isToBeDeleted()) {
                particlesToRemove.add(particle);
                continue;
            }
            particle.tick();
        }

        for (Particle particle : particlesToRemove) particles.remove(particle);
    }

    private void tickEntities() {
        //Keep track of which entities need to be removed - can't remove inside our loop, so we remove after
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            if (!onScreenEntities.contains(entity) || entity.isToBeDeleted() || entity.getY() > deathHeight) {
                entitiesToRemove.add(entity);
                continue;
            }
            entity.tick(this);
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
        //Find tickable tiles that aren't in our tick list and add them
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                Tile tile = tiles[x][y];
                if (onScreenTiles.contains(tile)) tile.tick(this);
                if (tile.isToBeDeleted()) {
                    //If the tile is an ItemBlockTile, we want to replace it with a new empty item block. Otherwise we simply remove it from the tick list
                    if (tile instanceof ItemBlockTile) {
                        EmptyItemBlockTile newTile = new EmptyItemBlockTile(tile.getxTile(), tile.getyTile());
                        tiles[tile.getxTile()][tile.getyTile()] = newTile;
                    } else {
                        tiles[tile.getxTile()][tile.getyTile()] = new SkyTile(tile.getxTile(), tile.getyTile());
                    }

                    if (tile instanceof BreakableTile) {
                        Particle[] blockParticles = new BlockParticleSet(tile.getxTile(), tile.getyTile()).getParticles();
                        for (Particle particle : blockParticles) {
                            particles.add(particle);
                        }
                    }
                }
            }
        }
    }

    public void createItem(Item item, int xTile, int yTile) {
        //If it is a PowerUpItem, evaluate Mario's current PowerUpState and spawn a mushroom if we're small
        if (item instanceof PowerUpItem) {
            if (player.getPowerUpState(PowerUpState.SMALL)) items.add(new MushroomItem(xTile * 16, yTile * 16));
            else items.add(new FireFlowerItem(xTile * 16, yTile * 16));
        }

        //If we aren't spawning a power up item, spawn an animated coin above the block and give a coin to the player
        else {
            items.add(new BlockCoinItem(xTile * 16, yTile * 16 - 16));
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
                if (powerUpItem.inSpawnAnimation()) screen.drawTile(SkyTile.NAME, (int) powerUpItem.getX(), powerUpItem.getInitialY());
            }
        }

        //Render tiles
        onScreenTiles.clear();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                Tile tile = tiles[x][y];
                if (!screen.isOffScreen(x * 16, 0)) {
                    onScreenTiles.add(tile);
                    if (tile instanceof AnimatedTile && ((AnimatedTile)tile).isAnimating()) {
                        //Subtract the animation frame from the Y coordinate
                        screen.drawTile(tile.getName(), x * 16, y * 16 - ((AnimatedTile)tile).getAnimationFrame());
                    }
                    //Don't draw sky tiles or they'll cover up our items
                    else if (!(tile instanceof SkyTile)) screen.drawTile(tile.getName(), x * 16, y * 16);
                }
            }
        }

        screen.drawHud(player.getCoinCount(), player.getScore(), timeRemaining, levelName);

        //Render particles
        for (Particle particle : particles) {
            screen.drawParticle(particle.getSprite(), (int) particle.getX(), (int) particle.getY());
            if (screen.isOffScreen((int) particle.getX(), (int) particle.getY())) particle.setToBeDeleted();
        }

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
