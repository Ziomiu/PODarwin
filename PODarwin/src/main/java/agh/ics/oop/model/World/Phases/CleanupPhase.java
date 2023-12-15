package agh.ics.oop.model.World.Phases;

import agh.ics.oop.model.Classes.Animal;
import agh.ics.oop.model.Classes.Grass;
import agh.ics.oop.model.World.Layers.MapLayer;

public class CleanupPhase implements Phase {
    private Animal[] removedAnimals;
    private Grass[] eatenGrass;

    @Override
    public void accept(MapLayer layer) {

    }

    public Animal[] getRemovedAnimals() {
        return removedAnimals;
    }

    public Grass[] getEatenGrass() {
        return eatenGrass;
    }
}
