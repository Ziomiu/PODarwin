package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.AnimalStats;
import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.world.phases.SummaryPhase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StatsGenerator {
    private SummaryPhase summaryPhase;
    private ArrayList<String> stats = new ArrayList<>();

    public StatsGenerator(SummaryPhase summaryPhase) {
        this.summaryPhase = summaryPhase;
    }

    public GlobalStatsEvent generateStats() {
        int day = summaryPhase.getDay();
        int grassCount = summaryPhase.getGrass().size();
        int animalCount = summaryPhase.getAnimals().size();
        int tunnelsCount = summaryPhase.getTunnels().size();
        int placesLeft = summaryPhase.getMapBoundary().numberOfFields() - tunnelsCount - grassCount;
        double averageEnergy = getAverageEnergy();
        double averageLifeTime = getAverageLifeTime();
        double averageChildren = getAverageChildren();
        String averageGenotype = getAverageGenotype();
        stats.add(String.valueOf(day));
        stats.add(String.valueOf(grassCount));
        stats.add(String.valueOf(animalCount));
        stats.add(String.valueOf(tunnelsCount));
        stats.add(String.valueOf(placesLeft));
        stats.add(String.valueOf(BigDecimal.valueOf(getAverageEnergy()).setScale(2, RoundingMode.HALF_UP)));
        stats.add(String.valueOf(BigDecimal.valueOf(getAverageLifeTime()).setScale(2, RoundingMode.HALF_UP)));
        stats.add(String.valueOf(BigDecimal.valueOf(getAverageChildren()).setScale(2, RoundingMode.HALF_UP)));
        stats.add(averageGenotype);
        return new GlobalStatsEvent(day, grassCount, animalCount, averageGenotype, tunnelsCount, placesLeft,
            averageLifeTime, averageChildren, averageEnergy);
    }

    public ArrayList<String> getStringStats() {
        return stats;
    }

    private double getAverageEnergy() {
        Set<Animal> animals = summaryPhase.getAnimals();
        double averageEnergy = animals.stream()
            .mapToDouble(Animal::getEnergy)
            .average()
            .orElse(0.0);
        return averageEnergy;
    }

    private double getAverageLifeTime() {
        Set<Animal> animals = summaryPhase.getDeadAnimals();
        double averageLifeTime = animals.stream()
            .map(Animal::getAnimalStats)
            .mapToInt(AnimalStats::getAge)
            .average()
            .orElse(0.0);
        return averageLifeTime;
    }

    private double getAverageChildren() {

        Set<Animal> animals = summaryPhase.getAnimals();
        double averageChildren = animals.stream()
            .map(Animal::getAnimalStats)
            .mapToDouble(AnimalStats::getNumOfChildren)
            .average()
            .orElse(0.0);
        return averageChildren;
    }

    private String getAverageGenotype() {
        Set<Animal> animals = summaryPhase.getAnimals();
        Map<List<Genome>, Long> movesCount = animals.stream()
            .map(Animal::getGenes)
            .collect(Collectors.groupingBy(moves -> moves, Collectors.counting()));
        long maxOccurrences = movesCount.values().stream()
            .mapToLong(Long::longValue)
            .max()
            .orElse(0);
        List<List<Genome>> maxOccurrencesMoves = movesCount.entrySet().stream()
            .filter(entry -> entry.getValue() == maxOccurrences)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        StringBuilder result = new StringBuilder();
        if (maxOccurrencesMoves.isEmpty()) {
            return result.toString();
        }
        for (Genome genome : maxOccurrencesMoves.get(0)) {
            result.append(genome.ordinal());
        }
        return result.toString();
    }
}
