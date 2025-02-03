package com.nate.mario.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private HashMap<String, int[]> spritesToLoad;

    public SpriteSheet(String path) { //Path for our "sheet_tile_ids'" file. File should be formatted as "sprite name, x coord, y coord, tile width, tile height"
        spritesToLoad = new HashMap<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.startsWith("//")) continue;
                    if (line.isBlank()) continue;
                    String[] parts = line.split(",");
                    for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

                    int width = 1;
                    int height = 1;

                    if (parts.length == 5) {
                        width = Integer.parseInt(parts[3]);
                        height = Integer.parseInt(parts[4]);
                    }

                    spritesToLoad.put(parts[0], new int[] {
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        width,
                        height
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, BufferedImage> getSprites(String path, int spriteSize) {
        HashMap<String, BufferedImage> sprites = new HashMap<>();
        BufferedImage spriteSheet = null;

        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, int[]> sprite : spritesToLoad.entrySet()) {
            String spriteName = sprite.getKey();
            int[] coords = sprite.getValue();

            int spriteX = coords[0];
            int spriteY = coords[1];
            int spriteWidth = coords[2];
            int spriteHeight = coords[3];

            BufferedImage spriteImage = new BufferedImage(spriteSize * spriteWidth, spriteSize * spriteHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = spriteImage.createGraphics();

            for (int x = 0; x < spriteWidth; x++) {
                for (int y = 0; y < spriteHeight; y++) {
                    g2d.drawImage(spriteSheet.getSubimage((spriteX + x) * (spriteSize + 1), (spriteY + y) * (spriteSize + 1), spriteSize, spriteSize), x * spriteSize, y * spriteSize, null);
                }
            }

            sprites.put(spriteName, spriteImage);
            g2d.dispose();
        }

        return sprites;
    }
}
