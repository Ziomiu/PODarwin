package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Drawable;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.HashMap;

public class DisplayPhase implements Phase {
    private MapChangeSubscriber mapChangeSubscriber;
    private HashMap<Integer, Drawable[]> drawableElements;
    private Boundary mapBoundary = null;

    @Override
    public void accept(MapLayer layer) {
        System.out.println("DISPLAY");
        layer.handle(this);
    }

    public MapChangeSubscriber getMapChangeSubscriber() {
        return mapChangeSubscriber;
    }

    public HashMap<Integer, Drawable[]> getDrawableElements() {
        return drawableElements;
    }

    public void setMapChangeSubscriber(MapChangeSubscriber subscriber) {
        this.mapChangeSubscriber = subscriber;
    }

    public void setMapBoundary(Boundary boundary) {
        mapBoundary = boundary;
    }
}
