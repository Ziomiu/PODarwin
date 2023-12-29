package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.ArrayList;
import java.util.HashSet;

public class EatPhase implements Phase {
    private HashSet<Grass> grassPositions;

    public HashSet<Grass> getGrassPositions() {
        return grassPositions;
    }

    public void setGrassPositions(HashSet<Grass> grassPositions) {
        this.grassPositions = grassPositions;
    }

    @Override
    public void accept(MapLayer layer) {

    }
}
