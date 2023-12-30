package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.world.phases.*;

public interface MapLayer {
    default boolean handle(InitPhase phase) {
        return false;
    }

    default boolean handle(CleanupPhase phase) {
        return false;
    }

    default boolean handle(MovePhase phase) {
        return false;
    }

    default boolean handle(EatPhase phase) {
        return false;
    }

    default boolean handle(ReproducePhase phase) {
        return false;
    }

    default boolean handle(GrowGrassPhase phase) {
        return false;
    }

    default boolean handle(DisplayPhase phase) {
        return false;
    }

    default MapLayer getNext() {
        return null;
    }

    default MapLayer setNext(MapLayer phase) {
        return null;
    }
}
