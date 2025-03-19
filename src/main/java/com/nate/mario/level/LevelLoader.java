package com.nate.mario.level;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class LevelLoader {

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

    private static int getLevelNumber(String levelName) {
        int worldNumber = Integer.parseInt(Character.toString(levelName.charAt(0)));
        int levelNumber = Integer.parseInt(Character.toString(levelName.charAt(2)));

        //Since there are always 4 levels in a world, we can find the sequential level number from the file name
        return (worldNumber - 1) * 4 + levelNumber - 1;
    }
}
