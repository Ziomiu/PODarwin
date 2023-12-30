package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.HashSet;

public class GrowGrassPhase implements Phase {
    private HashSet<Vector2D> blockedFields;
    private Boundary mapBoundary = null;

    @Override
    public void accept(MapLayer layer) {

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
}
