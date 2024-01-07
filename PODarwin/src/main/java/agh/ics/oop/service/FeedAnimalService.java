package agh.ics.oop.service;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.utils.AnimalComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FeedAnimalService {
    public static HashSet<Grass> eatGrass(HashMap<Vector2D, Grass> grassPositions, HashSet<Animal> animals) {
        HashMap<Vector2D, ArrayList<Animal>> groupedAnimals = groupAnimalsEat(animals);
        HashSet<Grass> removedGrass = new HashSet<>();
        AnimalComparator animalComparator = new AnimalComparator();
        for (Vector2D position : groupedAnimals.keySet()) {
            if (grassPositions.containsKey(position)) {
                groupedAnimals.get(position).sort(animalComparator);
                groupedAnimals
                    .get(position)
                    .get(groupedAnimals.get(position).size() - 1)
                    .addEnergy(grassPositions.get(position).getEnergy());
                groupedAnimals
                    .get(position)
                    .get(groupedAnimals.get(position).size() - 1)
                    .getAnimalStats().increasePlantsConsumed();
                System.out.println("Grass was eaten on position " + position);
                removedGrass.add(grassPositions.get(position));
            }
        }
        return removedGrass;
    }

    private static HashMap<Vector2D, ArrayList<Animal>> groupAnimalsEat(HashSet<Animal> animals) {
        HashMap<Vector2D, ArrayList<Animal>> groupedAnimals = new HashMap<>();
        for (Animal animal : animals) {
            Vector2D position = animal.getPosition();
            groupedAnimals.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
        }
        return groupedAnimals;
    }
}
