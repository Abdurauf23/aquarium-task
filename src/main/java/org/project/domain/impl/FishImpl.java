package org.project.domain.impl;

import lombok.Data;
import lombok.SneakyThrows;
import org.project.domain.Aquarium;
import org.project.domain.Fish;
import org.project.domain.Gender;
import org.project.domain.Position;
import org.project.utils.Parser;
import org.project.utils.RandomUtil;

import java.util.List;

@Data
public class FishImpl implements Fish {
    public static int FISH_COUNT = 0;

    private final Aquarium aquarium;
    private final Gender gender;
    private final int fishID;
    private final int sleepMS;
    private static boolean logPosition;
    private Position position;
    private int cyclesLeft;

    private FishImpl(boolean logPosition, Aquarium aquarium, Position position, Gender gender, int cyclesLeft, int sleepMS) {
        FishImpl.logPosition = logPosition;
        this.fishID = ++FISH_COUNT;
        this.aquarium = aquarium;
        this.position = position;
        this.gender = gender;
        this.cyclesLeft = cyclesLeft;
        this.sleepMS = sleepMS;
    }

    public static Fish createFish(Aquarium aquarium, Gender gender, int cyclesLeft, int sleepMS, boolean logPosition) {
        return new FishImpl(
                logPosition,
                aquarium,
                Parser.parseToPosition(RandomUtil.getRandomPoint(
                        aquarium.getDimensions().get(0),
                        aquarium.getDimensions().get(1),
                        aquarium.getDimensions().get(2))),
                gender,
                cyclesLeft,
                sleepMS);
    }

    public static Fish createFish(Aquarium aquarium, Gender gender, int cyclesLeft, int sleepMS) {
        return createFish(aquarium, gender, cyclesLeft, sleepMS, logPosition);
    }

    private void move() {
        // get new position
        List<Integer> dimensions = aquarium.getDimensions();
        List<Integer> position = RandomUtil.getRandomPoint(dimensions.get(0), dimensions.get(1), dimensions.get(2));

        // log if needed
        logOldPositionAndChangeToNew(position);
    }

    private boolean reduceCycle() {
        if (isStillAlive()) {
            cyclesLeft--;
            return true;
        } else {
            System.out.println("Fish with ID = " + this.fishID + " " + this.gender + " finished cycle");
            return false;
        }
    }

    @Override
    public boolean isStillAlive() {
        return cyclesLeft > 0;
    }

    private void logOldPositionAndChangeToNew(List<Integer> newPoint) {
        if (logPosition) {
            Position oldPosition = this.position;
            this.position = Parser.parseToPosition(newPoint);
            System.out.println("Fish with ID = " + this.fishID + " " + this.gender + " changes position from " +
                               oldPosition + " to " + this.position + " cycles left = " + cyclesLeft);
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        while (reduceCycle()) {
            move();
            Thread.sleep(sleepMS);
        }
    }
}
