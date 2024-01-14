package agh.ics.oop;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.visualization.MapChangeEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.model.visualization.StatsSubscriber;
import agh.ics.oop.model.world.layers.MapLayer;
import agh.ics.oop.model.world.phases.*;
import agh.ics.oop.utils.StatsGenerator;

import java.util.*;

public class Simulation implements Runnable {
    private HashMap<Animal, Vector2D> animalMoves;
    private MapLayer firstLayer;
    private final HashSet<Vector2D> permanentlyBlockedFields;
    private final List<MapChangeSubscriber> mapChangeSubscribers;
    private final List<StatsSubscriber<GlobalStatsEvent>> globalStatsSubscribers;
    private int day = 0;

    public Simulation() {
        animalMoves = new HashMap<>();
        permanentlyBlockedFields = new HashSet<>();
        mapChangeSubscribers = new LinkedList<>();
        globalStatsSubscribers = new LinkedList<>();
    }

    public void initializeMapLayers(MapLayer firstLayer) {
        this.firstLayer = firstLayer;
    }

    public void run() {
        if (firstLayer == null) {
            return;
        }

        bootstrapSimulation();

        try {
            for (var i = 0; i < 100; i++) {
                day++;
                System.out.printf("===== day %d =====%n", day);
                advanceSimulation();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMapChangeSubscriber(MapChangeSubscriber subscriber) {
        mapChangeSubscribers.add(subscriber);
    }

    public void addGlobalStatsSubscriber(StatsSubscriber<GlobalStatsEvent> subscriber) {
        globalStatsSubscribers.add(subscriber);
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

        SummaryPhase summaryPhase = new SummaryPhase();
        summaryPhase.setDay(day);
        visitLayers(summaryPhase);

        MapChangeEvent mapChangeEvent = new MapChangeEvent(
            day,
            summaryPhase.getMapBoundary(),
            summaryPhase.getAnimals(),
            summaryPhase.getGrass(),
            summaryPhase.getTunnels()
        );

        for (var subscriber : mapChangeSubscribers) {
            subscriber.onMapChange(mapChangeEvent);
        }
        for (var subscriber : globalStatsSubscribers) {
            subscriber.updateStats(summaryPhase.getGlobalStats());
        }
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
