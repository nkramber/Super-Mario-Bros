package com.nate.mario.level.tile;

public class EmptyItemBlockTile extends Tile {

    private int animationFrame = 0;
    private boolean doneAnimating = false;
    private boolean animatingDown = false;

    public EmptyItemBlockTile(int id, String name, boolean solid) {
        super(id, name, solid);
    }
    
    public EmptyItemBlockTile(int xTile, int yTile, int id, String name, boolean solid) {
        super(xTile, yTile, id, name, solid);
    }
    
    @Override
    public Tile newTile(int x, int y, int id, String name, boolean solid) {
        return new ItemBlockTile(x, y, id, name, solid);
    }

    public void doneAnimating() { doneAnimating = true; }
    public void increaseAnimationFrame() { animationFrame++; }
    public void decreaseAnimationFrame() { animationFrame--; }
    public void setAnimatingDown() { animatingDown = true; }

    public int getAnimationFrame() { return animationFrame; }
    public boolean isAnimatingDown() { return animatingDown; }
    public boolean isDoneAnimating() { return doneAnimating; }
}
