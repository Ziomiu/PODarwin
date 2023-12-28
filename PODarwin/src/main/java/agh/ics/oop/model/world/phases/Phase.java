package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.world.layers.MapLayer;

public interface Phase {
    void accept(MapLayer layer);
}
