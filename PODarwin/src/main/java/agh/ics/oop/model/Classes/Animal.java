package agh.ics.oop.model.Classes;

import agh.ics.oop.model.EnumClasses.Genome;

public class Animal implements WorldElement, Drawable {
    private Vector2D position;
    private int energy;
    private final GenomeSequence genomeSequence;
    private Genome currentGenome;

    public Animal(Vector2D initialPosition, GenomeSequence genomeSequence, int energy) {
        this.position = initialPosition;
        this.genomeSequence = genomeSequence;
        if (energy <= 0) {
            throw new IllegalStateException("Living animal should have more than 0 energy");
        }
        this.energy = energy;
        this.currentGenome = genomeSequence.nextInSequence();
    }

    @Override
    public void draw() {
        // todo
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
}
