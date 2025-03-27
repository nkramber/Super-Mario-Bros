package com.nate.mario.particle;

import com.nate.mario.gfx.sprite.Sprite;

public class BlockParticleSet {

    private BlockParticle[] particles = new BlockParticle[4];

    private static final float START_HOR_VELOCITY = 0.75f;
    private static final float START_VER_VELOCITY = -4.0f;

    public BlockParticleSet(int xTile, int yTile) {
        //Top left particle
        particles[0] = new BlockParticle(xTile * 16 - 4, yTile * 16 - 10, -START_HOR_VELOCITY, START_VER_VELOCITY * 1.25f);
        //Top right particle
        particles[1] = new BlockParticle(xTile * 16 + 12, yTile * 16 - 10, START_HOR_VELOCITY, START_VER_VELOCITY * 1.25f);
        //Bottom left particle
        particles[2] = new BlockParticle(xTile * 16 - 4, yTile * 16 + 6, -START_HOR_VELOCITY, START_VER_VELOCITY);
        //Bottom right particle
        particles[3] = new BlockParticle(xTile * 16 + 12, yTile * 16 + 6, START_HOR_VELOCITY, START_VER_VELOCITY);
    }

    public Particle[] getParticles() {
        return particles;
    }


    public class BlockParticle extends Particle {

        private static final Sprite[] sprite = {
            new Sprite("block_particle_1"),
            new Sprite("block_particle_2"),
            new Sprite("block_particle_3"),
            new Sprite("block_particle_4")
        };

        private int animationFrame = 0;

        public BlockParticle(float x, float y, float dirX, float dirY) {
            super(x, y, dirX, dirY);
        }

        @Override
        public void tick() {
            animationFrame++;
            if (animationFrame == 32) animationFrame = 0;

            if (dirY + VER_ACCEL_RATE > VER_MAX_SPEED) dirY = VER_MAX_SPEED;
            else dirY += VER_ACCEL_RATE;

            x += dirX;
            y += dirY;
        }

        @Override
        public Sprite getSprite() {
            return sprite[animationFrame / 8];
        }
    }
}
