package org.project.domain;

public interface Fish extends Runnable {
    boolean isStillAlive();
    Position getPosition();
    void run();
    int getFishID();
    Gender getGender();
}
