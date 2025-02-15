package com.nate.mario.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nate.mario.entity.Entity;
import com.nate.mario.entity.player.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.sprite.PlayerSprite;
import com.nate.mario.item.AnimatedPowerUpItem;
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

    private static final int TIME_TICK_INTERVAL = 400;
    private final int deathHeight;
    private final int dyingAnimationHeight;

    public Tile[][] tiles;
    public List<Item> items;
    
    private BufferedImage levelImage;
    private String levelName;
    private Color levelType;
    
    private Player player;
    private int playerSpawnX, playerSpawnY;
    private List<Entity> entities;
    private List<Entity> onScreenEntities;
    
    private long timeInMillis;
    private int timeRemaining;
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

        deathHeight = tiles[0].length * 16 - 32;
        dyingAnimationHeight = tiles[0].length * 16 + 128;
    }

    public Level newLevel(int playerLives) {
        Level newLevel = new Level(getLevelImage(), getLevelName());
        return newLevel;
    }

    public void tick(boolean[] keys) {
        if (player.isInDyingAnimation()) {
            if (player.getY() <= dyingAnimationHeight) {
                player.updateAnimation();
                player.getMovement(keys, this);
                player.move();
            } else {
                resetLevel = true;
            }
            return;
        }

        decrementTime();
        // if (player.getY() > deathHeight) {
            // resetLevel = true;
        // }

        if (timeRemaining == 0 || player.isDead() || player.getY() > deathHeight) {
            player.setxDir(0);
            player.setyDir(0);
            player.setNotOnGround();
            player.setInDyingAnimation();
            return;
        }

        if (player.isDoingPowerUpAnimation()) {
            player.updateAnimation();
            return;
        }

        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.isToBeDeleted()) {
                entitiesToRemove.add(entity);
            }
            
            if (onScreenEntities.contains(entity) || entity instanceof Player) {
                entity.getMovement(keys, this);
                entity.doTileCollisions(getLocalCollisionTiles(entity));
                entity.doEntityCollisions(entities);
                entity.move();
            }

            if (entity instanceof Player) {
                List<Item> collisionItems = getLocalCollisionItems((Player) entity);
                player.doItemCollisions(collisionItems);
            }

            entity.updateAnimation();
        }

        for (Entity entity : entitiesToRemove) entities.remove(entity); //add death animation to goombas

        List<Item> itemsToRemove = new ArrayList<>();
        for (Item item : items) {
            if (item.isToBeDeleted()) {
                itemsToRemove.add(item);
                continue;
            }

            if (item instanceof MushroomItem) {
                MushroomItem mushroomItem = (MushroomItem) item;
                mushroomItem.getMovement();
                if (!mushroomItem.inSpawnAnimation()) {
                    mushroomItem.doTileCollisions(getLocalCollisionTiles(item));
                }
            }

            if (item instanceof AnimatedPowerUpItem) ((AnimatedPowerUpItem) item).updateAnimationFrame();
            if (item instanceof PowerUpItem) ((PowerUpItem) item).move();
        }

        for (Item item : itemsToRemove) items.remove(item);

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
                        if (itemToCreate instanceof MushroomItem) {
                            if (PlayerSprite.MARIO_SMALL.contains(player.getSprite())) {
                                items.add(itemToCreate.newItem(itemBlockTile.getxTile() * 16, itemBlockTile.getyTile() * 16));
                            } else {
                                FireFlowerItem fireFlowerItem = (FireFlowerItem) Item.items.get("fire_flower");
                                items.add(fireFlowerItem.newItem(itemBlockTile.getxTile() * 16, itemBlockTile.getyTile() * 16));
                            }
                        }
                        else items.add(itemToCreate.newItem(itemBlockTile.getxTile() * 16, itemBlockTile.getyTile() * 16));
                    }

                    if (itemBlockTile.isToBeDeleted()) {
                        tiles[itemBlockTile.getxTile()][itemBlockTile.getyTile()] = new EmptyItemBlockTile(itemBlockTile.getxTile(), itemBlockTile.getyTile(), -1, "emptyitemblock", true);
                    }
                }
            }
        }
    }

    private Tile[][] getLocalCollisionTiles(Entity entity) {
        int xBoundLeft = (int) (entity.getX() - 16) / 16;
        int xBoundRight = (int) (entity.getX() + 31) / 16;
        int yBoundTop = (int) (entity.getY() - 16) / 16;
        int yBoundBottom = (int) (entity.getY() + 31 + entity.getHeight() - 16) / 16;

        Tile[][] collisionTiles = new Tile[xBoundRight - xBoundLeft + 1][yBoundBottom - yBoundTop + 1];

        int x = 0;
        for (int i = xBoundLeft; i <= xBoundRight; i++) {
            int y = 0;
            for (int j = yBoundTop; j <= yBoundBottom; j++) {
                collisionTiles[x][y] = tiles[i][j];
                y++;
            }
            x++;
        }

        return collisionTiles;
    }

    private Tile[][] getLocalCollisionTiles(Item item) {
        int xBoundLeft = (int) (item.getX() - 16) / 16;
        int xBoundRight = (int) (item.getX() + 31) / 16;
        int yBoundTop = (int) (item.getY() - 16) / 16;
        int yBoundBottom = (int) (item.getY() + 31) / 16;

        Tile[][] collisionTiles = new Tile[xBoundRight - xBoundLeft + 1][yBoundBottom - yBoundTop + 1];

        int x = 0;
        for (int i = xBoundLeft; i <= xBoundRight; i++) {
            int y = 0;
            for (int j = yBoundTop; j <= yBoundBottom; j++) {
                collisionTiles[x][y] = tiles[i][j];
                y++;
            }
            x++;
        }

        return collisionTiles;
    }

    private List<Item> getLocalCollisionItems(Entity entity) {
        int xBoundLeft = (int) (entity.getX() - 16);
        int xBoundRight = (int) (entity.getX() + 31);
        int yBoundTop = (int) (entity.getY() - 16);
        int yBoundBottom = (int) (entity.getY() + 31 + entity.getHeight() - 16);

        List<Item> collisionItems = new ArrayList<>();

        for (Item item : items) {
            int itemX = (int) item.getX();
            int itemY = (int) item.getY();

            if (itemX > xBoundLeft && itemX < xBoundRight && itemY > yBoundTop && itemY < yBoundBottom) {
                collisionItems.add(item);
            }
        }

        return collisionItems;
    }

    private void decrementTime() {
        if (player.isDoingPowerUpAnimation()) {
            timeInMillis = System.currentTimeMillis();
            return;
        }

        if (timeInMillis == 0) {
            timeInMillis = System.currentTimeMillis();
        } else {
            long currentTimeInMillis = System.currentTimeMillis();
            if (currentTimeInMillis - timeInMillis >= TIME_TICK_INTERVAL) {
                timeRemaining--;
                timeInMillis += TIME_TICK_INTERVAL;
            }
        }
    }

    public void render(Screen screen) {
        screen.setScroll((int) player.getX(), tiles.length);
        screen.setBackgroundColor(levelType);

        for (Item item : items) {
            screen.drawItem(item.getSprite(), (int) item.getX(), (int) item.getY());
            if (item instanceof PowerUpItem) {
                PowerUpItem powerUpItem = (PowerUpItem) item;
                if (screen.isOffScreen((int) powerUpItem.getX(), (int) powerUpItem.getY())) {
                    powerUpItem.setToBeDeleted();
                    continue;
                }
                if (powerUpItem.inSpawnAnimation()) screen.drawTile("sky", (int) powerUpItem.getX(), powerUpItem.getInitialY());
            }
        }

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                Tile tile = tiles[x][y];
                if (tile instanceof EmptyItemBlockTile && ((EmptyItemBlockTile)tile).isAnimating()) {
                    screen.drawTile(tile.getName(), x * 16, y * 16 - ((EmptyItemBlockTile)tile).getAnimationFrame());
                }
                else if (!(tile instanceof SkyTile)) screen.drawTile(tile.getName(), x * 16, y * 16);
            }
        }

        screen.drawHud(player.getCoinCount(), player.getScore(), timeRemaining, levelName);

        onScreenEntities.clear();
        for (Entity entity : entities) {
            if (!screen.isOffScreen((int) entity.getX(), (int) entity.getY())) {
                onScreenEntities.add(entity);
                entity.render(screen);
            }
        }
    }

    public void addPlayer(Player player) { 
        this.player = player;
        entities.add(0, player);
    }

    public Tile[][] getTiles() { return tiles; }
    public boolean isLevelFinished() { return levelFinished; }
    public boolean isGameOver() { return gameOver; }
    public boolean resetLevel() { return resetLevel; }
    public String getLevelName() { return levelName; }
    public int getPlayerSpawnX() { return playerSpawnX; }
    public int getPlayerSpawnY() { return playerSpawnY; }
    public BufferedImage getLevelImage() { return levelImage; }
}
