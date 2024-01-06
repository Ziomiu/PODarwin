package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Vector2D;

import java.util.HashMap;
import java.util.HashSet;

public class MoveAnimalService {
    private final HashSet<Animal> animals;
    private final HashMap<Animal, Vector2D> moves;

    public MoveAnimalService(HashSet<Animal> animals, HashMap<Animal, Vector2D> moves) {
        this.animals = animals;
        this.moves = moves;
    }

    public HashMap<Animal, Vector2D> moveAnimals() {
        HashMap<Animal, Vector2D> animalMoves = new HashMap<>();
        for (Animal animal : this.animals) {
            animal.setPosition(moves.get(animal));
            animal.removeEnergy(1);
            animal.getAnimalStats().increaseAge();
            animalMoves.put(animal, animal.getPosition().add(animal.nextGenome().toUnitVector()));
        }
        return animalMoves;
    }
}
