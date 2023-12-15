package agh.ics.oop.model.World.Layers;

import agh.ics.oop.model.World.Phases.*;

public class HoleLayer implements MapLayer {
    @Override
    public boolean handle(InitAnimalsPhase phase) {
        return false;
    }

    @Override
    public boolean handle(CleanupPhase phase) {
        return false;
    }

    @Override
    public boolean handle(MovePhase phase) {
        return false;
    }

    @Override
    public boolean handle(EatPhase phase) {
        return false;
    }

    @Override
    public boolean handle(ReproducePhase phase) {
        return false;
    }

    @Override
    public boolean handle(GrowGrassPhase phase) {
        return false;
    }

    @Override
    public boolean handle(DisplayPhase phase) {
        return false;
    }

    @Override
    public MapLayer getNext() {
        return null;
    }

    @Override
    public MapLayer setNext(MapLayer phase) {
        return null;
    }
}
