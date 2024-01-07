package agh.ics.oop;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.layers.MapLayer;
import agh.ics.oop.model.world.phases.*;

import java.util.HashMap;
import java.util.HashSet;

public class Simulation {
    private HashMap<Animal, Vector2D> animalMoves;
    private MapLayer firstLayer;
    private final HashSet<Vector2D> permanentlyBlockedFields;

    public Simulation() {
        animalMoves = new HashMap<>();
        permanentlyBlockedFields = new HashSet<>();
    }

    public void runOnLayers(MapLayer firstLayer) {
        this.firstLayer = firstLayer;
        bootstrapSimulation();

        for (var i = 0; i < 100; i++) {
            System.out.printf("===== day %d =====%n", i);
            advanceSimulation();
        }
    }

    private void advanceSimulation() {
        MovePhase movePhase = new MovePhase();
        movePhase.setNewAnimalMoves(animalMoves);
        visitLayers(movePhase);
        animalMoves = movePhase.getNewAnimalMoves();

        EatPhase eatPhase = new EatPhase();
        visitLayers(eatPhase);

        ReproducePhase reproducePhase = new ReproducePhase();
        visitLayers(reproducePhase);

        CleanupPhase cleanupPhase = new CleanupPhase();
        visitLayers(cleanupPhase);
        cleanupPhase.getRemovedAnimals().forEach(animalMoves::remove);

        GrowGrassPhase growGrassPhase = new GrowGrassPhase();
        // todo: can we do it better?
        growGrassPhase.setEatenGrass(cleanupPhase.getEatenGrass());
        growGrassPhase.setBlockedFields(permanentlyBlockedFields);
        visitLayers(growGrassPhase);
    }

    private void bootstrapSimulation() {
        InitPhase initPhase = new InitPhase();
        visitLayers(initPhase);
        initPhase.getAnimals().forEach(a -> animalMoves.put(a, a.getPosition().add(a.getCurrentGenome().toUnitVector())));
        initPhase.getHoles().forEach((hIn, hOut) -> {
            permanentlyBlockedFields.add(hIn);
            permanentlyBlockedFields.add(hOut);
        });
    }

    private void visitLayers(Phase phase) {
        var currentLayer = firstLayer;
        while (currentLayer != null) {
            phase.accept(currentLayer);
            currentLayer = currentLayer.getNext();
        }
    }
}
