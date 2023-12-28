package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.world.layers.MapLayer;

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
