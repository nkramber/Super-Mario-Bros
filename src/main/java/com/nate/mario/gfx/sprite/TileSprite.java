package com.nate.mario.gfx.sprite;

public class TileSprite extends Sprite {
    
    public static final TileSprite[] QMARK_BLOCK = {
        new TileSprite("qmark_block_tile_1"),
        new TileSprite("qmark_block_tile_1"),
        new TileSprite("qmark_block_tile_2"),
        new TileSprite("qmark_block_tile_3"),
        new TileSprite("qmark_block_tile_2"),
        new TileSprite("qmark_block_tile_1"),
        new TileSprite("qmark_block_tile_1")
    };

    public static final TileSprite[] HUD_COIN = {
        new TileSprite("coin_1"),
        new TileSprite("coin_1"),
        new TileSprite("coin_2"),
        new TileSprite("coin_3"),
        new TileSprite("coin_2"),
        new TileSprite("coin_1"),
        new TileSprite("coin_1")
    };

    public TileSprite(String name) {
        super(name);
    }
}
