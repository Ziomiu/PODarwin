package agh.ics.oop.model.visualization;

public record GlobalStatsEvent(
    int day,
    int totalGrass,
    int totalAnimals,
    String mostPopularGenome,
    int totalTunnels,
    int placesLeft,
    double averageLifetime,
    double averageChildren,
    double averageEnergy
) {
}
