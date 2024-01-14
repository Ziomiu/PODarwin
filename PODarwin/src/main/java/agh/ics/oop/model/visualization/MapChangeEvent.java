package agh.ics.oop.model.visualization;

import agh.ics.oop.model.classes.*;

import java.util.Set;

public record MapChangeEvent(
    int day,
    Boundary worldBoundary,
    Set<Animal> animals,
    Set<Grass> grass,
    Set<Hole> tunnels
) {
}
