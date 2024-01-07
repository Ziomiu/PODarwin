package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CleanupPhase implements Phase {
    private ArrayList<Animal> removedAnimals = new ArrayList<>();
    private HashSet<Grass> eatenGrass;

    @Override
    public void accept(MapLayer layer) {
        System.out.println("CLEANUP");
        layer.handle(this);
    }

    // todo: add setter and use it somewhere?
    public ArrayList<Animal> getRemovedAnimals() {
        return removedAnimals;
    }

    public HashSet<Grass> getEatenGrass() {
        return eatenGrass;
    }

    public void setEatenGrass(HashSet<Grass> eatenGrass) {
        this.eatenGrass = eatenGrass;
    }
}
