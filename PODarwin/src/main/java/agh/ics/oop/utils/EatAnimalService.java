package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class EatAnimalService {
    private HashMap<Vector2D, Grass> grassPositions;
    private HashSet<Animal> animals;

    public EatAnimalService(HashMap<Vector2D, Grass> grassPositions, HashSet<Animal> animals) {
        this.grassPositions = grassPositions;
        this.animals = animals;
    }

    private HashMap<Vector2D, ArrayList<Animal>> groupAnimalsEat() {
        HashMap<Vector2D, ArrayList<Animal>> groupedAnimals = new HashMap<>();
        for (Animal animal : animals) {
            Vector2D position = animal.getPosition();
            groupedAnimals.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
        }
        return groupedAnimals;
    }

    public HashSet<Grass> eatGrass() {
        HashMap<Vector2D, ArrayList<Animal>> groupedAnimals = groupAnimalsEat();
        HashSet<Grass> removedGrass = new HashSet<>();
        AnimalComparator animalComparator = new AnimalComparator();
        for (Vector2D position : groupedAnimals.keySet()) {
            if (grassPositions.containsKey(position)) {
                Collections.sort(groupedAnimals.get(position), animalComparator);
                groupedAnimals.get(position).get(groupedAnimals.get(position).size() - 1).addEnergy(grassPositions.get(position).getEnergy());
                groupedAnimals.get(position).get(groupedAnimals.get(position).size() - 1).getAnimalStats().increasePlantsConsumed();
                removedGrass.add(grassPositions.get(position));
            }
        }
        return removedGrass;
    }
}
