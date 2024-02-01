package org.project.domain.impl;

import lombok.Data;
import org.project.domain.Aquarium;
import org.project.domain.Fish;
import org.project.utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;

@Data
public class AquariumImpl implements Aquarium {
    private final int length, width, height;
    private final int sleepMS;
    private final boolean logBirth;
    private List<Fish> fishes;
    private final int fishLifeCycles;

    public AquariumImpl(List<Integer> dimensions, int sleepMS, boolean logBirth, int fishLifeCycles) {
        this.length = dimensions.get(0);
        this.width = dimensions.get(1);
        this.height = dimensions.get(2);
        this.sleepMS = sleepMS;
        this.logBirth = logBirth;
        this.fishLifeCycles = fishLifeCycles;
    }

    private List<Fish> checkNewFishes() {
        List<Fish> fishList = new ArrayList<>();

        for (int i = 0; i < fishesLeft(); i++) {
            for (int j = 0; j < fishesLeft(); j++) {
                Fish fish1 = fishes.get(i);
                Fish fish2 = fishes.get(j);
                if (fish1.getPosition().equals(fish2.getPosition()) && !fish1.getGender().equals(fish2.getGender())) {
                    logNewFishAndAdd(fishList, fish1, fish2);
                }
            }
        }
        return fishList;
    }

    @Override
    public List<Integer> getDimensions() {
        return List.of(getLength(), getWidth(), getHeight());
    }

    private int fishesLeft() {
        return fishes.size();
    }

    private List<Fish> deadFishes() {
        return fishes.stream().filter(fish -> !fish.isStillAlive()).toList();
    }

    @Override
    public void start() {
        try {
            fishes.stream().map(Thread::new).toList().forEach(Thread::start);
            while (fishesLeft() > 0) {
                fishes.addAll(checkNewFishes());
                fishes.removeAll(deadFishes());

                Thread.sleep(sleepMS);
            }
            System.out.println("No fishes left in the Aquarium");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void logNewFishAndAdd(List<Fish> fishList, Fish fish1, Fish fish2) {
        if (logBirth) {
            Fish fish = FishImpl.createFish(this, RandomUtil.randomGender(), fishLifeCycles, sleepMS);
            fishList.add(fish);
            System.out.println("New Fish with ID = " + fish.getFishID() + " born from fishes "
                               + fish1.getFishID() + " and " + fish2.getFishID());
            new Thread(fish).start();
        }
    }
}
