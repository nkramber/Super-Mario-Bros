package com.nate.mario.level.tile;

public class EmptyItemBlockTile extends Tile {

    private int animationFrame = 0;
    private boolean animatingDown = false;

    public EmptyItemBlockTile(int id, String name, boolean solid) {
        super(id, name, solid);
    }
    
    public EmptyItemBlockTile(int xTile, int yTile, int id, String name, boolean solid) {
        super(xTile, yTile, id, name, solid);
        animating = true;
    }
    
    @Override
    public Tile newTile(int x, int y, int id, String name, boolean solid) {
        return new ItemBlockTile(x, y, id, name, solid);
    }

    public void animate() {
        if (animationFrame < 0) {
            animating = false;
        } else if (animatingDown) {
            animationFrame--;
        } else if (!animatingDown) {
            if (animationFrame == 4) animatingDown = true;
            else animationFrame++;
        }
    }

    public int getAnimationFrame() { return animationFrame; }
}
