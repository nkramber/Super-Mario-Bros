package com.nate.mario.item.powerupitem.playerstateitem;

import com.nate.mario.gfx.sprite.ItemSprite;
import com.nate.mario.item.Item;

public class FireFlowerItem extends PlayerStateItem {

    public static final int ID = -1;
    private static final ItemSprite[] SPRITES = ItemSprite.FIRE_FLOWER;

    public FireFlowerItem(float x, float y) {
        super(x, y);
    }

    @Override public ItemSprite getSprite() { return SPRITES[animationFrame / 2]; }
    @Override public Item newItem(float x, float y) { return new FireFlowerItem(x, y); }
}
