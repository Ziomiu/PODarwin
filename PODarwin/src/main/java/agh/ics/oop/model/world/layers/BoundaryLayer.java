package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.Boundary;

public class BoundaryLayer implements MapLayer {
    private final Boundary boundary;
    private final boolean wrapWorld;

    public BoundaryLayer(Boundary boundary, boolean wrapWorld) {
        this.boundary = boundary;
        this.wrapWorld = wrapWorld;
    }
}
