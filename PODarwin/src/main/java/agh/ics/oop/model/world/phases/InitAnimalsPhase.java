package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.world.layers.MapLayer;

public class InitAnimalsPhase implements Phase {
    private Boundary mapBoundary = null;

    @Override
    public void accept(MapLayer layer) {
        layer.handle(this);
    }

    public void setMapBoundary(Boundary boundary) {
        mapBoundary = boundary;
    }
}
