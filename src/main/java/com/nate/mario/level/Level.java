package com.nate.mario.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.nate.mario.MarioGame;
import com.nate.mario.entity.Entity;
import com.nate.mario.entity.Player;
import com.nate.mario.gfx.Screen;
import com.nate.mario.level.tile.Tile;

public class Level {

    public Tile[][] tiles;
    public String levelName;
    public int levelNumber;

    private Player player;
    private List<Entity> entities;

    public Level(BufferedImage levelImage, String levelName) {
        this.levelName = levelName;

        int width = levelImage.getWidth();
        int height = levelImage.getHeight();
        
        tiles = new Tile[width][height];
        entities = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(levelImage.getRGB(x, y));
                for (Tile tile : Tile.tiles) {
                    if (color.getRed() == tile.getID()) {
                        tiles[x][y] = tile.newTile(x, y, tile.getID(), tile.getName(), tile.isSolid());
                        break;
                    }
                }
                if (tiles[x][y] == null) throw new IllegalArgumentException("Tile does not exist at " + x + ", " + y + " on level " + levelName);
            }
        }
    }

    public void tick(MarioGame game, boolean[] keys) {
        for (Entity entity : entities) {
            entity.doTileCollisions(getCollisionTiles(entity));
            entity.getMovement(keys);
        }
    }

    private Tile[][] getCollisionTiles(Entity entity) {
        int xBoundLeft = (int) (entity.getX() + entity.getxDir() - 1) / 16;
        int xBoundRight = (int) (entity.getX() + entity.getxDir() + 17) / 16;
        int yBoundTop = (int) (entity.getY() + entity.getyDir() - 1 - (entity.getHeight() - 1) * 16) / 16;
        int yBoundBottom = (int) (entity.getY() + entity.getyDir() + 17) / 16;
        
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


    public void render(Screen screen) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                screen.drawTile(tiles[x][y].getName(), x * 16, y * 16);
            }
        }

        for (Entity entity : entities) entity.render(screen);
    }

    public void addPlayer(Player player) { 
        this.player = player;
        entities.add(0, player);
    }
    
    public void addMob(Entity entity) { entities.add(entity); }
}
