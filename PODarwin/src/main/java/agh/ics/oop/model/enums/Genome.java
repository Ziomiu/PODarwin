package agh.ics.oop.model.enums;

import agh.ics.oop.model.classes.Vector2D;

import java.util.List;

/*
Genomes, map directions and relative rotations are the same.
Consecutive genomes should be "applied" to the previous genome by "rotating" it.
 */
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

    public Vector2D toUnitVector() {
        return switch (this) {
            case NORTH_EAST -> new Vector2D(1, 1);
            case EAST -> new Vector2D(1, 0);
            case WEST -> new Vector2D(-1, 0);
            case NORTH -> new Vector2D(0, 1);
            case SOUTH_EAST -> new Vector2D(1, -1);
            case SOUTH -> new Vector2D(0, -1);
            case SOUTH_WEST -> new Vector2D(-1, -1);
            case NORTH_WEST -> new Vector2D(-1, 1);
        };
    }
}
