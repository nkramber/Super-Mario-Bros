package com.nate.mario.particle;

import com.nate.mario.gfx.sprite.Sprite;

public class BlockParticleSet {

    private BlockParticle[] particles = new BlockParticle[4];

    public BlockParticleSet(int xTile, int yTile) {
        //Top left particle
        particles[0] = new BlockParticle(xTile * 16, yTile * 16, -0.5f, -4.0f);
        //Top right particle
        particles[1] = new BlockParticle(xTile * 16 + 16, yTile * 16, 0.5f, -4.0f);
        //Bottom left particle
        particles[2] = new BlockParticle(xTile * 16, yTile * 16 + 16, -0.5f, -4.0f);
        //Bottom right particle
        particles[3] = new BlockParticle(xTile * 16 + 16, yTile * 16 + 16, 0.5f, -4.0f);
    }

    public Particle[] getParticles() {
        return particles;
    }


    public class BlockParticle extends Particle {

        public BlockParticle(float x, float y, float dirX, float dirY) {
            super(x, y, dirX, dirY, new Sprite("block_particle"));
        }
    }
}
