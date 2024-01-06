package agh.ics.oop.model.classes;

import java.util.HashMap;
import java.util.HashSet;

public class AnimalStats {
    private int plantsConsumed = 0;
    private HashSet<Animal> parents = new HashSet<>();
    private int numOfChildren = 0;
    private int numOfDescendants = 0;
    private int age = 0;
    private int deathDay = 0;

    public AnimalStats() {
    }

    public void increasePlantsConsumed() {
        this.plantsConsumed += 1;
    }

    public void increaseAge() {
        this.age += 1;
    }

    public void addDescendant() {
        this.numOfChildren += 1;
        this.numOfDescendants += 1;
        if (!parents.isEmpty()) {
            for (Animal animal : this.parents) {
                if (animal.getAnimalStats().deathDay != 0) {
                    animal.getAnimalStats().addDescendant();
                }
            }
        }
    }

    public int getPlantsConsumed() {
        return plantsConsumed;
    }

    public HashSet<Animal> getParents() {
        return parents;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public int getNumOfDescendants() {
        return numOfDescendants;
    }

    public int getAge() {
        return age;
    }

    public int getDeathDay() {
        return deathDay;
    }
    //Add setting deathDay when Simulation is ready
}
