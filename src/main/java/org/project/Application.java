package org.project;

import lombok.ToString;
import org.project.domain.Aquarium;
import org.project.domain.Fish;
import org.project.domain.Gender;
import org.project.domain.impl.AquariumImpl;
import org.project.domain.impl.FishImpl;
import org.project.utils.PropertyReader;
import org.project.utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ToString
public class Application {
    // default values
    private static final String LOG_POSITION = "true";
    private static final String LOG_BIRTH = "true";
    private static final String MAX_DIMENSIONS = "20";
    private static final String FISH_LIFECYCLES = "15";
    private static final String FISH_NUMBER = "10";
    private static final String SLEEP_MS = "2000";

    // variables
    private final boolean logPosition;
    private final boolean logBirth;
    private final int maxDimensions;
    private final int fishLifeCycles;
    private final int fishNumber;
    private final int sleepMS;

    // app fields
    private Aquarium aquarium;
    private List<Fish> fishes;

    public Application() {
        Map<String, String> properties = PropertyReader.readProperties();

        this.logPosition = Boolean.parseBoolean(properties.getOrDefault("logPosition", LOG_POSITION));
        this.logBirth = Boolean.parseBoolean(properties.getOrDefault("logBirth", LOG_BIRTH));
        this.maxDimensions = Integer.parseInt(properties.getOrDefault("maxDimensions", MAX_DIMENSIONS));
        this.fishLifeCycles = Integer.parseInt(properties.getOrDefault("fishLifeCycles", FISH_LIFECYCLES));
        this.fishNumber = Integer.parseInt(properties.getOrDefault("fishNumber", FISH_NUMBER));
        this.sleepMS = Integer.parseInt(properties.getOrDefault("sleepMS", SLEEP_MS));
    }

    public void start() {

        // initialize
        List<Integer> dimensions = RandomUtil.getRandomPoint(maxDimensions, maxDimensions, maxDimensions);
        System.out.println("Creating Aquarium with dimensions " + dimensions);

        aquarium = new AquariumImpl(
                dimensions,
                sleepMS,
                logBirth,
                fishLifeCycles
        );
        fishes = new ArrayList<>();

        // fill aquarium with fishes
        int maleNumber = RandomUtil.getRandomNumber(fishNumber);
        int femaleNumber = RandomUtil.getRandomNumber(fishNumber);
        generateFishes(Gender.MALE, maleNumber);
        generateFishes(Gender.FEMALE, femaleNumber);

        aquarium.setFishes(fishes);
        aquarium.start();
    }

    private void generateFishes(Gender gender, int number) {
        for (int i = 0; i < number; i++) {
            fishes.add(FishImpl.createFish(aquarium, gender, fishLifeCycles, sleepMS, logPosition));
        }
        System.out.println("Created " + number + " " + gender + " fishes");
    }
}
