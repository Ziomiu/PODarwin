package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.world.layers.MapLayer;

public class ReproducePhase implements Phase {
    @Override
    public void accept(MapLayer layer) {
        System.out.println("REPRODUCE");
        layer.handle(this);
    }
}
