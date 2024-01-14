package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.model.visualization.AnimalStatsEvent;

public class SingleStatsGenerator {
    private Animal animal;

    public SingleStatsGenerator(Animal animal) {
        this.animal = animal;
    }

    public AnimalStatsEvent getStats() {
        String fullGenome = getFullGenome();
        String currentGenome = String.valueOf(animal.getCurrentGenome().ordinal());
        int currentEnergy = animal.getEnergy();
        int eatenGrass = animal.getAnimalStats().getPlantsConsumed();
        int childCount = animal.getAnimalStats().getNumOfChildren();
        int descendantCount = animal.getAnimalStats().getNumOfDescendants();
        int lifetime = animal.getAnimalStats().getAge();
        int deathDay = animal.getAnimalStats().getDeathDay();
        return new AnimalStatsEvent(fullGenome, currentGenome, currentEnergy, eatenGrass, childCount
            , descendantCount, lifetime, deathDay);
    }

    private String getFullGenome() {
        StringBuilder result = new StringBuilder();
        for (Genome genome : animal.getGenomeSequence().getAllGenomes()) {
            result.append(genome.ordinal());
        }
        return result.toString();
    }
}
