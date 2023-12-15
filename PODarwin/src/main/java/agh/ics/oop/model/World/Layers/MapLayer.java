package agh.ics.oop.model.World.Layers;

import agh.ics.oop.model.World.Phases.*;

public interface MapLayer {
    boolean handle (InitAnimalsPhase phase);
    boolean handle (CleanupPhase phase);
    boolean handle (MovePhase phase);
    boolean handle (EatPhase phase);
    boolean handle (ReproducePhase phase);
    boolean handle (GrowGrassPhase phase);
    boolean handle (DisplayPhase phase);
    MapLayer getNext();
    MapLayer setNext(MapLayer phase);
}
