package com.nate.mario.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
    private List<Entity> mobs;

    public Level(BufferedImage levelImage, String levelName) {
        this.levelName = levelName;
        this.levelNumber = getLevelNumber(levelName);

        int width = levelImage.getWidth();
        int height = levelImage.getHeight();
        
        tiles = new Tile[width][height];
        mobs = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(levelImage.getRGB(x, y));
                for (Tile tile : Tile.tiles) {
                    if (color.getRed() == tile.redID && color.getGreen() == tile.greenID) {
                        tiles[x][y] = tile.newTile(x, y, tile.name);
                    }
                }
                if (tiles[x][y] == null) throw new IllegalArgumentException("Tile does not exist at " + x + ", " + y + " on level " + levelName);
            }
        }
    }

    public void tick(MarioGame game, boolean[] keys) {
        player.tick(keys);
        for (Entity entity : mobs) entity.tick();
    }

    public void render(Screen screen) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                screen.drawTile(tiles[x][y].name, x * 16, y * 16);
            }
        }

        for (Entity entity : mobs) entity.render(screen);
        player.render(screen);
    }

    public static int getLevelNumber(String levelName) {
        int worldNumber = Integer.parseInt(Character.toString(levelName.charAt(0)));
        int levelNumber = Integer.parseInt(Character.toString(levelName.charAt(2)));

        return (worldNumber - 1) * 4 + levelNumber - 1;
    }

    public static List<Level> loadLevels() {
        List<Level> levels = new ArrayList<>();
        URL url = Level.class.getResource("/levels");
        File directory = new File(url.getPath());
        File[] files = directory.listFiles();

        for (File file : files) {
            try {
                String levelName = file.getName().replace(".png", "");
                BufferedImage levelImage = ImageIO.read(file);

                levels.add(new Level(levelImage, levelName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return levels;
    }

    public void addPlayer(Player player) { this.player = player; }
    public void addMob(Entity entity) { mobs.add(entity); }
}
