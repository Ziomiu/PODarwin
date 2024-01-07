package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.HashMap;
import java.util.HashSet;

public class InitPhase implements Phase {
    private Boundary mapBoundary = null;
    private HashMap<Vector2D, Vector2D> holes = new HashMap<>();
    private HashSet<Grass> grasses = null;
    private HashSet<Animal> animals = null;

    @Override
    public void accept(MapLayer layer) {
        System.out.println("INIT");
        layer.handle(this);
    }

    public void setMapBoundary(Boundary boundary) {
        mapBoundary = boundary;
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public HashMap<Vector2D, Vector2D> getHoles() {
        return holes;
    }

    public void setHoles(HashMap<Vector2D, Vector2D> holes) {
        this.holes = holes;
    }

    public HashSet<Grass> getGrasses() {
        return grasses;
    }

    public void setGrasses(HashSet<Grass> grasses) {
        this.grasses = grasses;
    }

    public HashSet<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(HashSet<Animal> animals) {
        this.animals = animals;
    }
}
