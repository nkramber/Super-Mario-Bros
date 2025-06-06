package com.nate.mario.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.nate.mario.Main;
import com.nate.mario.gfx.sprite.Sprite;
import com.nate.mario.gfx.sprite.TileSprite;

public class Screen {

    //How many dummy pixels (tiles * 16) are below each level
    private static final int VERTICAL_OFFSET = -96;

    //How many dummy pixels (tiles * 16) are on the left side of each level
    public static final int SCREEN_LEFT_PADDING = -32;

    private Graphics2D g;

    //Maps of all of our different sprites
    private HashMap<String, BufferedImage> entitySprites;
    private HashMap<String, BufferedImage> leftFacingSprites;
    private HashMap<String, BufferedImage> tileSprites;
    private HashMap<String, BufferedImage> itemSprites;
    private HashMap<String, BufferedImage> hudSprites;
    private HashMap<String, BufferedImage> particleSprites;

    //How many pixels to offset the screen. This is based on the x coordinate of the player
    private int xScroll = SCREEN_LEFT_PADDING;

    public Screen(Graphics2D g) {
        this.g = g;
        entitySprites = new SpriteSheet("player_sprite_tile_ids.txt").getSprites("/sprites/player_sprites.png", 16);
        entitySprites.putAll(new SpriteSheet("enemy_sprite_tile_ids.txt").getSprites("/sprites/enemy_sprites.png", 16));
        tileSprites = new SpriteSheet("map_tile_ids.txt").getSprites("/sprites/tile_sprites.png", 16);
        itemSprites = new SpriteSheet("item_tile_ids.txt").getSprites("/sprites/item_sprites.png", 16);
        hudSprites = new SpriteSheet("hud_tile_ids.txt").getSprites("/sprites/hud_sprites.png", 8);
        particleSprites = new SpriteSheet("particle_tile_ids.txt").getSprites("/sprites/particle_sprites.png", 8);

        leftFacingSprites = new HashMap<>();
        for (Map.Entry<String, BufferedImage> sprite : entitySprites.entrySet()) {
            String name = sprite.getKey();
            BufferedImage spriteImage = sprite.getValue();

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-spriteImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            leftFacingSprites.put(name, op.filter(spriteImage, null));
        }
    }

    public void drawSprite(Sprite sprite, boolean facingLeft, int x, int y) {
        //Make sure the entity is on screen before rendering
        if (!isOffScreen(x, y)) {
            //Check the direction the entity is facing and render the appropriate sprite
            if (!facingLeft) {
                if (!entitySprites.containsKey(sprite.getName())) throw new IllegalArgumentException(sprite.getName() + " - sprite name does not exist!");
                else g.drawImage(entitySprites.get(sprite.getName()), x + xScroll, y + VERTICAL_OFFSET, null);
            } else {
                if (!leftFacingSprites.containsKey(sprite.getName())) throw new IllegalArgumentException(sprite.getName() + " - sprite name does not exist!");
                else g.drawImage(leftFacingSprites.get(sprite.getName()), x + xScroll, y + VERTICAL_OFFSET, null);
            }
        }
    }

    public void drawParticle(Sprite sprite, int x, int y) {
        g.drawImage(particleSprites.get(sprite.getName()), x + xScroll, y + VERTICAL_OFFSET, null);
    }

    // public void drawTile(String tileName, int x, int y) {
    public void drawTile(Sprite sprite, int x, int y) {
        //Make sure the tile is on screen before rendering
        if (!isOffScreen(x, y)) {
            if (!tileSprites.containsKey(sprite.getName())) throw new IllegalArgumentException(sprite.getName() + " - tile name does not exist!");
            else {
                g.drawImage(tileSprites.get(sprite.getName()), x + xScroll, y + VERTICAL_OFFSET, null);
            }
        }
    }

    public void drawItem(Sprite sprite, int x, int y) {
        //Make sure the item is on screen before rendering
        if (!isOffScreen(x, y)) {
            if (!itemSprites.containsKey(sprite.getName())) throw new IllegalArgumentException(sprite.getName() + " - item name does not exist!");
            else g.drawImage(itemSprites.get(sprite.getName()), x + xScroll, y + VERTICAL_OFFSET, null);
        }
    }

    public void drawHud(int coinCount, int score, int timeInFramesRemaining, String levelName) {
        drawHudBase(coinCount, score, levelName);
        drawHudNumber(Integer.toString(timeInFramesRemaining), 3, 26, 3);
    }

    public void drawGameOver(int coinCount, int score, String levelName) {
        drawHudBase(coinCount, score, levelName);
        drawHudText("game over", 11, 16);
    }

    public void drawHudBase(int coinCount, int score, String levelName) {
        drawHudIcons(new String[] {
            "coin",
            "x_icon",
            Integer.toString(coinCount / 10), //coins tenths place
            Integer.toString(coinCount % 10)  //coins ones place
        }, 11, 3);

        drawHudNumber(Integer.toString(score), 6, 3, 3);
        drawHudText(levelName, 19, 3);
        drawHudText("mario", 3, 2);
        drawHudText("world", 18, 2);
        drawHudText("time", 25, 2);
    }

    public void drawHudText(String hudText, int xHalfTile, int yHalfTile) {
        for (int i = 0; i < hudText.length(); i++) {
            g.drawImage(hudSprites.get(String.valueOf(hudText.charAt(i))), (xHalfTile + i) * 8, yHalfTile * 8, null);
        }
    }

    public void drawHudIcons(String[] hudText, int xHalfTile, int yHalfTile) {
        for (int i = 0; i < hudText.length; i++) {
            if (i == 0) g.drawImage(hudSprites.get(TileSprite.HUD_COIN[Sprite.getFlickerSprite()].getName()), (xHalfTile + i) * 8, yHalfTile * 8, null);
            g.drawImage(hudSprites.get(hudText[i]), (xHalfTile + i) * 8, yHalfTile * 8, null);
        }
    }

    public void drawHudNumber(String score, int numberLength, int xHalfTile, int yHalfTile) {
        if (score.length() < numberLength) {
            StringBuilder sb = new StringBuilder(score);
            while (sb.length() < numberLength) {
                sb.insert(0, "0");
            }
            score = sb.toString();
        }
        drawHudText(score, xHalfTile, yHalfTile);
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

    public boolean isOffScreen(int x, int y) {
        return (x + xScroll < -49 || x + xScroll >= Main.SCREEN_WIDTH + 33 || y < -33 || y > Main.SCREEN_HEIGHT - VERTICAL_OFFSET + 1);
    }

    public void resetScroll() { xScroll = SCREEN_LEFT_PADDING; }

    public Graphics2D getGraphics() { return g; }
}
