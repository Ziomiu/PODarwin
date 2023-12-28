package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.layers.MapLayer;

public class EatPhase implements Phase {
    private Vector2D[] grassPositions;

    public Vector2D[] getGrassPositions() {
        return grassPositions;
    }


    @Override
    public void accept(MapLayer layer) {

    }
}
