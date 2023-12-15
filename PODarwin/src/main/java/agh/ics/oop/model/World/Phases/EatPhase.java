package agh.ics.oop.model.World.Phases;

import agh.ics.oop.model.Classes.Vector2D;
import agh.ics.oop.model.World.Layers.MapLayer;

public class EatPhase implements Phase {
    private Vector2D[] grassPositions;

    public Vector2D[] getGrassPositions() {
        return grassPositions;
    }


    @Override
    public void accept(MapLayer layer) {

    }
}
