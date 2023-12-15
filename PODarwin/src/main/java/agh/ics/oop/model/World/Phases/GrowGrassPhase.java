package agh.ics.oop.model.World.Phases;

import agh.ics.oop.model.Classes.Vector2D;
import agh.ics.oop.model.World.Layers.MapLayer;

import java.util.HashSet;
import java.util.Vector;

public class GrowGrassPhase implements Phase {
    private HashSet<Vector2D> blockedFields;

    @Override
    public void accept(MapLayer layer) {

    }

    public HashSet<Vector2D> getBlockedFields() {
        return blockedFields;
    }
}
