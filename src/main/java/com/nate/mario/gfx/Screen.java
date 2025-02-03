package com.nate.mario.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.nate.mario.Main;

public class Screen {

    private static final int VERTICAL_OFFSET = -96;
    private static final int HORIZONTAL_OFFSET = -32;

    private Graphics2D g;
    private HashMap<String, BufferedImage> sprites;
    private HashMap<String, BufferedImage> tiles;
    private HashMap<String, BufferedImage> items;
    private HashMap<String, BufferedImage> hud;

    private int xScroll = HORIZONTAL_OFFSET;

    public Screen(Graphics2D g) {
        this.g = g;
        sprites = new SpriteSheet("sprite_tile_ids.txt").getSprites("/sprites/entity_sprites.png", 16);
        tiles = new SpriteSheet("map_tile_ids.txt").getSprites("/sprites/tile_sprites.png", 16);
        items = new SpriteSheet("item_tile_ids.txt").getSprites("/sprites/item_sprites.png", 16);
        hud = new SpriteSheet("hud_tile_ids.txt").getSprites("/sprites/hud_sprites.png", 8);

        System.out.println(hud.keySet());
    }

    public void drawSprite(String spriteName, int x, int y) {
        if (!sprites.containsKey(spriteName)) throw new IllegalArgumentException(spriteName + " - sprite name does not exist!");
        else g.drawImage(sprites.get(spriteName), x + xScroll, y + VERTICAL_OFFSET, null);
    }

    public void drawTile(String tileName, int x, int y) {
        if (!tiles.containsKey(tileName)) throw new IllegalArgumentException(tileName + " - tile name does not exist!");
        else {
            g.drawImage(tiles.get(tileName), x + xScroll, y + VERTICAL_OFFSET, null);
        }
    }

    public void drawItem(String itemName, int x, int y) {
        if (!items.containsKey(itemName)) throw new IllegalArgumentException(itemName + " - item name does not exist!");
        else g.drawImage(items.get(itemName), x + xScroll, y + VERTICAL_OFFSET, null);
    }

    public void drawText(String hudText, int xHalfTile, int yHalfTile) {
        for (int i = 0; i < hudText.length(); i++) {
            g.drawImage(hud.get(String.valueOf(hudText.charAt(i))), (xHalfTile + i) * 8, yHalfTile * 8, null);
        }
    }

    public void drawHud(String[] hudText, int xHalfTile, int yHalfTile) {
        for (int i = 0; i < hudText.length; i++) {
            g.drawImage(hud.get(hudText[i]), (xHalfTile + i) * 8, yHalfTile * 8, null);
        }
    }

    public void drawNumber(String score, int numberLength, int xHalfTile, int yHalfTile) {
        if (score.length() < numberLength) {
            StringBuilder sb = new StringBuilder(score);
            while (sb.length() < numberLength) {
                sb.insert(0, "0");
            }
            score = sb.toString();
        }
        drawText(score, xHalfTile, yHalfTile);
    }

    public void drawRect(Color color, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRect(x + xScroll, y + VERTICAL_OFFSET, width, height);
    }

    public void drawRect(Color color, Rectangle rect) {
        g.setColor(color);
        g.drawRect(rect.x + xScroll, rect.y + VERTICAL_OFFSET, rect.width, rect.height);
    }

    public void setScroll(int playerX, int levelWidth) {
        if (playerX + 32 > Main.SCREEN_WIDTH / 2) {
            int newXScroll = -playerX - 32 + Main.SCREEN_WIDTH / 2;
            if (newXScroll < xScroll) xScroll = newXScroll;
        }

        int maxXScroll = -levelWidth * 16 + 288;
        if (xScroll < maxXScroll) xScroll = maxXScroll;
    }

    public void setBackgroundColor(Color color) {
        g.setColor(color);
        g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    }

    public Graphics2D getGraphics() { return g; }
}
