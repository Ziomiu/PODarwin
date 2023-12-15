package agh.ics.oop.model.World.Phases;

import agh.ics.oop.model.World.Layers.MapLayer;

public interface Phase {
    void accept(MapLayer layer);
}
