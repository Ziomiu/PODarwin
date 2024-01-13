package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.Set;

public class SummaryPhase implements Phase {
    private Boundary mapBoundary = null;
    private Set<Animal> animals = Set.of();
    private Set<Grass> grass =  Set.of();
    private Set<Hole> tunnels = Set.of();


    @Override
    public void accept(MapLayer layer) {
        layer.handle(this);
    }

    public void setMapBoundary(Boundary boundary) {
        mapBoundary = boundary;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public Set<Grass> getGrass() {
        return grass;
    }

    public void setGrass(Set<Grass> grass) {
        this.grass = grass;
    }

    public Set<Hole> getTunnels() {
        return tunnels;
    }

    public void setTunnels(Set<Hole> tunnels) {
        this.tunnels = tunnels;
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }
}
