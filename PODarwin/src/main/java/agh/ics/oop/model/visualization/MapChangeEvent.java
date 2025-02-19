package agh.ics.oop.model.visualization;

import agh.ics.oop.model.classes.*;

import java.util.Set;

public record MapChangeEvent(
    int day,
    Boundary worldBoundary,
    Boundary preferredGrassFields,
    Set<Animal> animals,
    Set<Grass> grass,
    Set<Hole> tunnels,
    String mostPopularGenome
) {
}
