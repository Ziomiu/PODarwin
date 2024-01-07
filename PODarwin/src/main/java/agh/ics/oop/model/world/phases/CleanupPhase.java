package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.ArrayList;

public class CleanupPhase implements Phase {
    private ArrayList<Animal> removedAnimals;
    private ArrayList<Grass> eatenGrass;

    @Override
    public void accept(MapLayer layer) {
        System.out.println("CLEANUP");
        layer.handle(this);
    }

    public ArrayList<Animal> getRemovedAnimals() {
        return removedAnimals;
    }

    public ArrayList<Grass> getEatenGrass() {
        return eatenGrass;
    }
}
