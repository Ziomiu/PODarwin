package agh.ics.oop.model.World.Phases;

import agh.ics.oop.model.Classes.Animal;
import agh.ics.oop.model.Classes.Vector2D;
import agh.ics.oop.model.World.Layers.MapLayer;

import java.util.HashMap;

public class MovePhase implements Phase {
    private HashMap<Animal, Vector2D> newAnimalMoves;

    @Override
    public void accept(MapLayer layer) {
    }

    public HashMap<Animal, Vector2D> getNewAnimalMoves() {
        return newAnimalMoves;
    }
}

