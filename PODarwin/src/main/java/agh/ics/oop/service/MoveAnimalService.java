package agh.ics.oop.service;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Vector2D;

import java.util.HashMap;
import java.util.HashSet;

public class MoveAnimalService {
    public static HashMap<Animal, Vector2D> moveAnimals(HashSet<Animal> animals, HashMap<Animal, Vector2D> moves) {
        HashMap<Animal, Vector2D> animalMoves = new HashMap<>();
        for (Animal animal : animals) {
            animal.setPosition(moves.get(animal));
            animal.removeEnergy(1);
            animal.getAnimalStats().increaseAge();
            animalMoves.put(animal, animal.getPosition().add(animal.nextGenome().toUnitVector()));
        }
        return animalMoves;
    }
}
