package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.HashMap;

public class MovePhase implements Phase {
    private HashMap<Animal, Vector2D> newAnimalMoves;

    @Override
    public void accept(MapLayer layer) {
        System.out.println("MOVE");
        layer.handle(this);
    }

    public HashMap<Animal, Vector2D> getNewAnimalMoves() {
        return newAnimalMoves;
    }

    public void setNewAnimalMoves(HashMap<Animal, Vector2D> newAnimalMoves) {
        this.newAnimalMoves = newAnimalMoves;
    }
}

