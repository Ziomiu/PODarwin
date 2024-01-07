package agh.ics.oop.model.classes.factory;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.GenomeSequence;
import agh.ics.oop.model.classes.Vector2D;

public class AnimalFactory {
    private final int defaultEnergy;

    public AnimalFactory(
        int defaultEnergy
    ) {
        this.defaultEnergy = defaultEnergy;
    }

    public Animal getAnimal(Vector2D position, GenomeSequence genomeSequence) {
        return new Animal(position, genomeSequence, defaultEnergy);
    }

    public Animal getAnimal(Vector2D position, GenomeSequence genomeSequence, int energy) {
        return new Animal(position, genomeSequence, energy);
    }

    public Animal getAnimal(Vector2D position, GenomeSequence genomeSequence, int energy, Animal parent1, Animal parent2) {
        if (parent1 == parent2) {
            throw new IllegalArgumentException("Parents should be different");
        }

        return new Animal(position, genomeSequence, parent1, parent2, energy);
    }
}
