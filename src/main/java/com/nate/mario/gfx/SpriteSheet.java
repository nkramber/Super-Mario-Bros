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

    public SpriteSheet(String path) { //Path for our "sprites_to_load" file. File should be formatted as "sprite name, x coord, y coord, tile width, tile height"
        spritesToLoad = new HashMap<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

                    spritesToLoad.put(parts[0], new int[] {
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4])
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, BufferedImage> getSprites(String path) {
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

            BufferedImage spriteImage = new BufferedImage(16 * spriteWidth, 16 * spriteHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = spriteImage.createGraphics();

            for (int x = 0; x < spriteWidth; x++) {
                for (int y = 0; y < spriteHeight; y++) {
                    g2d.drawImage(spriteSheet.getSubimage((spriteX + x) * 17, (spriteY + y) * 17, 16, 16), x * 16, y * 16, null);
                }
            }

            sprites.put(spriteName, spriteImage);
            g2d.dispose();
        }

        return sprites;
    }
}
