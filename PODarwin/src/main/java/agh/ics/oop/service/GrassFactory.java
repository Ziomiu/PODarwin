package agh.ics.oop.service;

import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;

public class GrassFactory {
    private final int defaultGrassEnergy;

    public GrassFactory(int defaultGrassEnergy) {
        this.defaultGrassEnergy = defaultGrassEnergy;
    }

    public Grass getGrassPatch(Vector2D position) {
        return new Grass(position, defaultGrassEnergy);
    }
}
