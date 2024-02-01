package org.project.utils;

import org.project.domain.Gender;

import java.util.List;
import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static List<Integer> getRandomPoint(int maxX, int maxY, int maxZ) {
        return List.of(
                getRandomNumber(maxX),
                getRandomNumber(maxY),
                getRandomNumber(maxZ)
        );
    }

    public static int getRandomNumber(int max) {
        return random.nextInt(max) + 1;
    }

    public static Gender randomGender() {
        return random.nextInt(10) > 5 ? Gender.MALE : Gender.FEMALE;
    }
}
