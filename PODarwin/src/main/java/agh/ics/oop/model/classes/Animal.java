package agh.ics.oop.model.classes;

import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.utils.Pair;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Animal implements WorldElement, Drawable {
    private Vector2D position;
    private int energy;
    private final GenomeSequence genomeSequence;
    private Genome currentGenome;
    private Pair<Animal> parents;
    private final HashSet<Animal> descendants;
    private final AnimalStats animalStats = new AnimalStats();
    private final UUID fingerprint;

    public Animal(Vector2D initialPosition, GenomeSequence genomeSequence, Animal parent1, Animal parent2, int energy) {
        this.position = initialPosition;
        this.genomeSequence = genomeSequence;
        if (energy <= 0) {
            throw new IllegalStateException("Living animal should have more than 0 energy");
        }
        this.energy = energy;
        this.currentGenome = genomeSequence.nextInSequence();
        fingerprint = UUID.randomUUID();
        descendants = new HashSet<>();
        if (parent1 != null && parent2 != null) {
            this.parents = new Pair<>(parent1, parent2);
            this.parents.first().animalStats.increaseChildren();
            this.parents.second().animalStats.increaseChildren();
            updateParentsDescendants(this);
        }
    }

    public Animal(Vector2D initialPosition, GenomeSequence genomeSequence, int energy) {
        this(initialPosition, genomeSequence, null, null, energy);
    }

    @Override
    public Color getColor() {
        return Color.hsb(30, Math.max(0.2, Math.min(20 + energy, 100) / 100.0), Math.min(Math.max(energy, 40), 100) / 100.0);
    }

    public void setPosition(Vector2D newPosition) {
        position = newPosition;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    public void addEnergy(int energy) {
        if (energy < 0) {
            throw new IllegalArgumentException("Can't add negative energy");
        }
        this.energy += energy;
    }

    public void removeEnergy(int energy) {
        if (energy < 0) {
            throw new IllegalArgumentException("Can't remove negative energy");
        }
        this.energy = Math.max(0, this.energy - energy);
    }

    public int getEnergy() {
        return energy;
    }

    public Genome nextGenome() {
        currentGenome = genomeSequence.nextInSequence();
        return currentGenome;
    }

    public Genome getCurrentGenome() {
        return currentGenome;
    }

    public void setCurrentGenome(Genome genome) {
        currentGenome = genome;
    }

    public AnimalStats getAnimalStats() {
        return animalStats;
    }

    @Override
    public String toString() {
        return this.getPosition().toString();
    }

    public List<Genome> getGenes() {
        return genomeSequence.getAllGenomes();
    }

    public GenomeSequence getGenomeSequence() {
        return genomeSequence;
    }

    public boolean isDead() {
        return animalStats.getDeathDay() != 0;
    }

    private void updateParentsDescendants(Animal newAnimal) {
        if (parents == null) {
            return;
        }

        for (Animal parent : parents.getAsList()) {
            if (parent.descendants.contains(newAnimal) || parent.isDead()) {
                continue;
            }

            parent.descendants.add(newAnimal);
            parent.animalStats.increaseDescendants();
            parent.updateParentsDescendants(newAnimal);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return Objects.equals(fingerprint, animal.fingerprint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fingerprint);
    }
}
