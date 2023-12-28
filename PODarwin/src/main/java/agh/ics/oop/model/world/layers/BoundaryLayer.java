package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.model.world.phases.DisplayPhase;
import agh.ics.oop.model.world.phases.GrowGrassPhase;
import agh.ics.oop.model.world.phases.InitAnimalsPhase;
import agh.ics.oop.model.world.phases.MovePhase;

public class BoundaryLayer implements MapLayer {
    private final Boundary boundary;
    private final boolean wrapWorld;

    public BoundaryLayer(Boundary boundary, boolean wrapWorld) {
        this.boundary = boundary;
        this.wrapWorld = wrapWorld;
    }

    @Override
    public boolean handle(InitAnimalsPhase phase) {
        phase.setMapBoundary(boundary);
        return true;
    }

    @Override
    public boolean handle(MovePhase phase) {
        var moves = phase.getNewAnimalMoves();
        for (Animal animal : moves.keySet()) {
            var pos = moves.get(animal);
            if (boundary.contains(pos)) {
                continue;
            }

            if (!wrapWorld || !boundary.containsVertical(pos)) {
                animal.setCurrentGenome(animal.getCurrentGenome().applyRotation(Genome.SOUTH));
                moves.put(animal, animal.getPosition());
                continue;
            }

            if (pos.x() > boundary.upper().x()) {
                moves.put(animal, new Vector2D(boundary.lower().x(), pos.y())) ;
            } else {
                moves.put(animal, new Vector2D(boundary.upper().x(), pos.y())) ;
            }
        }
        return true;
    }

    @Override
    public boolean handle(GrowGrassPhase phase) {
        phase.setMapBoundary(boundary);
        return true;
    }

    @Override
    public boolean handle(DisplayPhase phase) {
        phase.setMapBoundary(boundary);
        return true;
    }
}
