package com.nate.mario.util;

public class Timer {

    private long startTime;
    private long elapsedTime;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public void tick() {
        long time = System.currentTimeMillis();
        elapsedTime = time - startTime;
    }

    public long getElapsedTime() { return elapsedTime; }
}
