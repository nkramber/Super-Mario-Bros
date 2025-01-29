package com.nate.mario.entity;

import java.awt.event.KeyEvent;

import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.Sprite;

public class Player extends Entity {

    private final int rightKey = KeyEvent.VK_D;
    private final int leftKey = KeyEvent.VK_A;
    private final int crouchKey = KeyEvent.VK_S;
    private final int jumpKey = KeyEvent.VK_BACK_SLASH;
    private final int actionKey = KeyEvent.VK_PERIOD;

    private String currentSprite = Sprite.MARIO_SMALL_STILL;

    public Player(float xTile, float yTile) {
        super(xTile, yTile);
    }

    @Override
    public void tick(boolean[] keys) {
        if (keys[rightKey]) System.out.println("right");
    }

    public void render(Screen screen) {
        screen.drawSprite(currentSprite, (int) x, (int) y);
    }

    public void test() {}
}
