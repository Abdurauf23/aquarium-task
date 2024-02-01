package org.project.domain.impl;

import lombok.Builder;
import lombok.Data;
import org.project.domain.Position;

@Data
@Builder
public class PositionImpl implements Position {
    private int x, y, z;
    @Override
    public String toString() {
        return "{x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
