package com.nate.mario.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Screen {

    private Graphics2D g;
    private HashMap<String, BufferedImage> sprites = new HashMap<>();

    public Screen(Graphics2D g) {
        this.g = g;
        sprites = new SpriteSheet("sprites_to_load.txt").getSpriteSheet("/sprites/spritesheet.png");
    }

    public void drawSprite(String spriteName, int x, int y) {
        if (!sprites.containsKey(spriteName)) System.out.println(spriteName + " sprite not found!");
        else g.drawImage(sprites.get(spriteName), x, y, null);
    }

    public Graphics2D getGraphics() { return g; }
}
