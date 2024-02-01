package org.project.domain;

import java.util.List;

public interface Aquarium {
    List<Integer> getDimensions();
    void setFishes(List<Fish> fishes);
    void start();
}
