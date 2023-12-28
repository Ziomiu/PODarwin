package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Drawable;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.HashMap;

public class DisplayPhase implements Phase {
    private MapChangeSubscriber mapChangeSubscriber;
    private HashMap<Integer, Drawable[]> drawableElements;

    @Override
    public void accept(MapLayer layer) {

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
}
