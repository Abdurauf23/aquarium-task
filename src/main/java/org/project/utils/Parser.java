package org.project.utils;

import org.project.domain.Position;
import org.project.domain.impl.PositionImpl;

import java.util.List;

public class Parser {
    public static Position parseToPosition(List<Integer> point) {
        return PositionImpl.builder()
                .x(point.get(0))
                .y(point.get(1))
                .z(point.get(2))
                .build();
    }
}
