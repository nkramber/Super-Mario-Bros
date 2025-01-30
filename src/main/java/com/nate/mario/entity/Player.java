package com.nate.mario.entity;

import java.awt.event.KeyEvent;

import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.Sprites;

public class Player extends Entity {

    private final int rightKey = KeyEvent.VK_D;
    private final int leftKey = KeyEvent.VK_A;
    private final int crouchKey = KeyEvent.VK_S;
    private final int jumpKey = KeyEvent.VK_BACK_SLASH;
    private final int actionKey = KeyEvent.VK_PERIOD;

    private String currentSprite = Sprites.MARIO_SMALL_STILL;
    private int width, height = 1 * 16;

    public Player(float xTile, float yTile) {
        super(xTile, yTile, 0, 0);
    }

    public void tick(boolean[] keys) {
        x += xDir;
        y += yDir;

        if (!keys[leftKey] && !keys[rightKey]) xDir = 0;
        if (keys[leftKey]) xDir -= 0.1f;
        if (keys[rightKey]) xDir += 0.1f;

        System.out.println(xDir + ", " + yDir);
    }

    public void render(Screen screen) {
        screen.drawSprite(currentSprite, (int) x, (int) y);
    }
}
