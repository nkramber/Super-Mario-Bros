package com.nate.mario.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nate.mario.entity.Entity;
import com.nate.mario.entity.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.item.Item;
import com.nate.mario.level.tile.EmptyItemBlockTile;
import com.nate.mario.level.tile.ItemBlockTile;
import com.nate.mario.level.tile.Tile;
import com.nate.mario.state.MarioGame;

public class Level {

    public Tile[][] tiles;
    public List<Item> items;
    
    private String levelName;
    private Color levelType;
    
    private Player player;
    private List<Entity> entities;

    private int timeInFramesRemaining;

    public Level(BufferedImage levelImage, String levelName) {
        this.levelName = levelName;

        int width = levelImage.getWidth();
        int height = levelImage.getHeight();
        
        tiles = new Tile[width][height];
        items = new ArrayList<>();
        entities = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(levelImage.getRGB(x, y));

                if (x == 0 && y == 0) {
                    if (color.getRGB() == LevelType.OVERWORLD.getRGB()) levelType = LevelType.OVERWORLD;

                    tiles[x][y] = Tile.tiles.get(0);
                    continue;
                }

                if (x == 0 && y == 1) {
                    timeInFramesRemaining = (color.getRed() + color.getGreen()) * 24 + 23;

                    tiles[x][y] = Tile.tiles.get(0);
                    continue;
                }

                for (Tile tile : Tile.tiles) {
                    if (color.getRed() == tile.getID()) {
                        tiles[x][y] = tile.newTile(x, y, tile.getID(), tile.getName(), tile.isSolid());
                        break;
                    }
                }

                for (Item item : Item.items) {
                    if (color.getGreen() == item.getID()) {
                        items.add(item.newItem(x * 16, y * 16, item.getID(), item.getName()));
                    }
                }

                if (tiles[x][y] == null) throw new IllegalArgumentException("Valid tile does not exist at " + x + ", " + y + " on level " + levelName);
            }
        }
    }

    public void tick(MarioGame game, boolean[] keys) {
        timeInFramesRemaining--;

        for (Entity entity : entities) {
            entity.getMovement(keys, this);
            entity.doTileCollisions(getCollisionTiles(entity));
            entity.move();
        }

        List<Item> collisionItems = getCollisionItems(player);
        player.doItemCollisions(collisionItems);
        for (Item item : collisionItems) {
            if (item.isToBeDeleted()) {
                items.remove(item);
            }
        }

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                Tile tile = tiles[x][y];

                if (tile instanceof EmptyItemBlockTile) ((EmptyItemBlockTile)tile).tick();

                if (tile.isToBeDeleted() && tile instanceof ItemBlockTile) {
                    tiles[tile.getxTile()][tile.getyTile()] = new EmptyItemBlockTile(tile.getxTile(), tile.getyTile(), -1, "emptyitemblock", true);
                }
            }
        }
    }

    private Tile[][] getCollisionTiles(Entity entity) {
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

    private List<Item> getCollisionItems(Entity entity) {
        int xBoundLeft = (int) (entity.getX() - 16);
        int xBoundRight = (int) (entity.getX() + 31);
        int yBoundTop = (int) (entity.getY() - 16);
        int yBoundBottom = (int) (entity.getY() + 31 + entity.getHeight() - 16);

        List<Item> collisionItems = new ArrayList<>();

        for (Item item : items) {
            if (item.getX() > xBoundLeft && item.getX() < xBoundRight && item.getY() > yBoundTop && item.getY() < yBoundBottom) {
                collisionItems.add(item);
            }
        }

        return collisionItems;
    }

    public void render(Screen screen) {
        screen.setScroll((int) player.getX(), tiles.length);
        screen.setBackgroundColor(levelType);

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                Tile tile = tiles[x][y];
                if (tile instanceof EmptyItemBlockTile && ((EmptyItemBlockTile)tile).isAnimating()) {
                    screen.drawTile(tile.getName(), x * 16, y * 16 - ((EmptyItemBlockTile)tile).getAnimationFrame());
                }
                else screen.drawTile(tile.getName(), x * 16, y * 16);
            }
        }

        for (Item item : items) screen.drawItem(item.getName(), item.getX(), item.getY());

        //Draw coin count
        screen.drawHud(new String[] {
            "coin",
            "x_icon",
            Integer.toString(player.getCoinCount() / 10), //coins tenths place
            Integer.toString(player.getCoinCount() % 10)  //coins ones place
        }, 11, 3);

        screen.drawNumber(Integer.toString(player.getScore()), 6, 3, 3);
        screen.drawNumber(Integer.toString(timeInFramesRemaining / 24), 3, 26, 3);
        screen.drawText(levelName, 19, 3);
        screen.drawText("mario", 3, 2);
        screen.drawText("world", 18, 2);
        screen.drawText("time", 25, 2);

        for (Entity entity : entities) entity.render(screen);
    }

    public void addPlayer(Player player) { 
        this.player = player;
        entities.add(0, player);
    }
    
    public void addMob(Entity entity) { entities.add(entity); }

    public Tile[][] getTiles() { return tiles; }
}
