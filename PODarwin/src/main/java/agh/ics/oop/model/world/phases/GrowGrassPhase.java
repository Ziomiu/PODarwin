package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.HashSet;

public class GrowGrassPhase implements Phase {
    private HashSet<Vector2D> blockedFields;
    private Boundary mapBoundary = null;
    private HashSet<Grass> eatenGrass;

    @Override
    public void accept(MapLayer layer) {
        layer.handle(this);
    }

    public HashSet<Vector2D> getBlockedFields() {
        return blockedFields;
    }

    public void setBlockedFields(HashSet<Vector2D> blockedFields) {
        this.blockedFields = blockedFields;
    }

    public void setMapBoundary(Boundary mapBoundary) {
        this.mapBoundary = mapBoundary;
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public HashSet<Grass> getEatenGrass() {
        return eatenGrass;
    }

    public void setEatenGrass(HashSet<Grass> eatenGrass) {
        this.eatenGrass = eatenGrass;
    }
}
