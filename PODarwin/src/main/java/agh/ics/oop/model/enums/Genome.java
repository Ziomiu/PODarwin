package agh.ics.oop.model.enums;

import java.util.List;

public enum Genome {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    private static final List<Genome> quickAccessValues;

    static {
        // Enum classes create values() array every time the method is called
        // this saves some processing because we will use it a lot
        quickAccessValues = List.of(Genome.values());
    }

    public Genome applyRotation(Genome g) {
        return quickAccessValues.get((this.ordinal() + g.ordinal()) % quickAccessValues.size());
    }
}
