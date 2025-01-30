package com.nate.mario.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

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

        int width = levelImage.getWidth();
        int height = levelImage.getHeight();
        
        tiles = new Tile[width][height];
        mobs = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(levelImage.getRGB(x, y));
                for (Tile tile : Tile.tiles) {
                    System.out.println(tile.toString() + ", " + x + ", " + y);
                    if (color.getRed() == tile.id) {
                        tiles[x][y] = tile.newTile(x, y, tile.id, tile.name, tile.solid);
                        break;
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

    public static HashMap<Integer, Level> loadLevels() {
        HashMap<Integer, Level> levels = new HashMap<>();

        try {
            URI uri = Level.class.getResource("/levels").toURI();
            Path levelsPath;
            FileSystem fileSystem = null;

            try {
                if (uri.getScheme().equals("jar")) {
                    fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    levelsPath = fileSystem.getPath("/levels");
                } else {
                    levelsPath = Paths.get(uri);
                }

                // Walk the path and load each PNG file
                try (Stream<Path> walk = Files.walk(levelsPath, 1)) {
                    walk.filter(path -> path.toString().toLowerCase().endsWith(".png"))
                            .forEach(path -> {
                                String fileName = path.getFileName().toString();
                                try (InputStream in = Level.class.getResourceAsStream("/levels/" + fileName)) {
                                    if (in != null) {
                                        String levelName = fileName.replace(".png", "");
                                        BufferedImage levelImage = ImageIO.read(in);
                                        levels.put(getLevelNumber(levelName), new Level(levelImage, levelName));
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            } finally {
                if (fileSystem != null) {
                    fileSystem.close();
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return levels;
    }

    public void addPlayer(Player player) { this.player = player; }
    public void addMob(Entity entity) { mobs.add(entity); }
}
