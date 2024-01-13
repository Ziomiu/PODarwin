package agh.ics.oop.model.visualization;

public record GlobalStatsEvent(
    int totalGrass,
    int totalAnimals,
    String mostPopularGenome,
    int totalTunnels,
    int placesLeft,
    double averageLifetime,
    double averageChildren
) {
}
