package com.nate.mario.entity;

import com.nate.mario.gfx.Screen;
import com.nate.mario.gfx.sprite.Sprite;

public class Goomba extends Entity {

    public static final int ID = 255;
    private static final int SCORE = 100;
    public static final Sprite WALKING = new Sprite("goomba_walk");
    public static final Sprite DEAD = new Sprite("goomba_dead");

    private static final float HORIZONTAL_SPEED = -0.7f;

    public Goomba(float xTile, float yTile) {
        super(xTile, yTile, HORIZONTAL_SPEED, 0, WALKING);
    }

    @Override
    public void render(Screen screen) {
        screen.drawSprite(currentSprite, facingLeft, (int) x, (int) y);
    }

    @Override
    protected void updateSprite() {
        if (inDyingAnimation) {
            if (currentSprite != DEAD) {
                currentSprite = DEAD;
                dirX = 0;
                return;
            } else {
                animationFrame++;
                if (animationFrame == 60) {
                    setToBeDeleted();
                }
            }
            return;
        }

        animationFrame++;
        if (animationFrame == 8) {
            facingLeft = !facingLeft;
            animationFrame = 0;
        }
    }

    @Override public Entity newEntity(int xTile, int yTile) { return new Goomba(xTile, yTile); }
    public int getScore() { return SCORE; }
}
