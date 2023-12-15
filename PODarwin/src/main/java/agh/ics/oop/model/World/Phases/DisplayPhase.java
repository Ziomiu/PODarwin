package agh.ics.oop.model.World.Phases;

import agh.ics.oop.model.Classes.Drawable;
import agh.ics.oop.model.Visualization.MapChangeSubscriber;
import agh.ics.oop.model.World.Layers.MapLayer;

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
