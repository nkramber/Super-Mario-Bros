package com.nate.mario.item.powerupitem.playerstateitem;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.Item;
import com.nate.mario.item.powerupitem.PowerUpItem;
import com.nate.mario.level.Level;

public abstract class PlayerStateItem extends PowerUpItem {

    public PlayerStateItem(float x, float y, boolean hasSpriteArray) {
        super(x, y, hasSpriteArray);
    }

    @Override public void tick(Level level) {
        super.tick(level);
    }

    @Override public abstract Item newItem(float x, float y);
    @Override public abstract ItemSprite getSprite();
}
