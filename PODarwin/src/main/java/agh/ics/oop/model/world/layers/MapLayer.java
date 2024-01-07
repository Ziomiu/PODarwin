package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.world.phases.*;

public interface MapLayer {
    default void handle(InitPhase phase) {}

    default void handle(CleanupPhase phase) {}

    default void handle(MovePhase phase) {}

    default void handle(EatPhase phase) {}

    default void handle(ReproducePhase phase) {}

    default void handle(GrowGrassPhase phase) {}

    default void handle(DisplayPhase phase) {}

    default MapLayer getNext() {
        return null;
    }

    default MapLayer setNext(MapLayer phase) {
        return null;
    }
}
