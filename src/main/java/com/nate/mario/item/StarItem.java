package com.nate.mario.item;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.powerupitem.PowerUpItem;
import com.nate.mario.level.Level;

public class StarItem extends PowerUpItem {

    public static final int ID = 200;
    public static final ItemSprite[] SPRITE = ItemSprite.STAR;

    public StarItem(float x, float y) {
        super(x, y);
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
        if (inSpawnAnimation) return;

        
    }

    @Override public Item newItem(float x, float y) { return new StarItem(x, y); }
    @Override public ItemSprite getSprite() { return SPRITE[animationFrame / 2]; }
}
