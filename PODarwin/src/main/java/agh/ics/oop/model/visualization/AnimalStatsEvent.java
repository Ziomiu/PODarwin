package agh.ics.oop.model.visualization;

public record AnimalStatsEvent(
    String fullGenome,
    String currentGenome,
    int currentEnergy,
    int eatenGrass,
    int childCount,
    int descendantsCount,
    int lifetime,
    int deathDay
) {
}
