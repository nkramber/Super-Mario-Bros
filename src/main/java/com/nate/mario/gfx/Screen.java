package com.nate.mario.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Screen {

    private static final int VERTICAL_OFFSET = 16;

    private Graphics2D g;
    private HashMap<String, BufferedImage> sprites;
    private HashMap<String, BufferedImage> tiles;

    public Screen(Graphics2D g) {
        this.g = g;
        sprites = new SpriteSheet("sprite_ids.txt").getSprites("/sprites/entity_sprites.png");
        tiles = new SpriteSheet("map_tile_ids.txt").getSprites("/sprites/tile_sprites.png");
    }

    public void drawSprite(String spriteName, int x, int y) {
        if (!sprites.containsKey(spriteName)) throw new IllegalArgumentException(spriteName + " - sprite name does not exist!");
        else g.drawImage(sprites.get(spriteName), x, y + VERTICAL_OFFSET, null);
    }

    public void drawTile(String tileName, int x, int y) {
        if (!tiles.containsKey(tileName)) throw new IllegalArgumentException(tileName + " - tile name does not exist!");
        else g.drawImage(tiles.get(tileName), x, y + VERTICAL_OFFSET, null);
    }

    public void drawRect(Color color, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRect(x, y + VERTICAL_OFFSET, width, height);
    }

    public Graphics2D getGraphics() { return g; }
}
