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

    private int xScroll = HORIZONTAL_OFFSET;

    public Screen(Graphics2D g) {
        this.g = g;
        sprites = new SpriteSheet("sprite_ids.txt").getSprites("/sprites/entity_sprites.png");
        tiles = new SpriteSheet("map_tile_ids.txt").getSprites("/sprites/tile_sprites.png");
        items = new SpriteSheet("item_tile_ids.txt").getSprites("/sprites/item_sprites.png");
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
