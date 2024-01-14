package agh.ics.oop;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.enums.SimulationStatus;
import agh.ics.oop.model.visualization.*;
import agh.ics.oop.model.world.layers.MapLayer;
import agh.ics.oop.model.world.phases.*;
import agh.ics.oop.utils.SingleStatsGenerator;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableDoubleValue;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class Simulation implements Runnable {
    private HashMap<Animal, Vector2D> animalMoves;
    private MapLayer firstLayer;
    private final HashSet<Vector2D> permanentlyBlockedFields;
    private final List<MapChangeSubscriber> mapChangeSubscribers;
    private final List<StatsSubscriber<GlobalStatsEvent>> globalStatsSubscribers;
    private final List<StatsSubscriber<AnimalStatsEvent>> animalStatsSubscribers;
    private final List<Consumer<SimulationStatus>> simulationStatusSubscribers;
    private int day = 0;
    private ObservableBooleanValue pauseState;
    private ObservableDoubleValue speedValue;
    private boolean pauseRequested = false;
    private boolean endRequested = false;
    private CountDownLatch pauseLatch;
    private Animal animalToFollow;

    public Simulation() {
        pauseLatch = new CountDownLatch(0);
        animalMoves = new HashMap<>();
        permanentlyBlockedFields = new HashSet<>();
        mapChangeSubscribers = new LinkedList<>();
        globalStatsSubscribers = new LinkedList<>();
        animalStatsSubscribers = new LinkedList<>();
        simulationStatusSubscribers = new LinkedList<>();
    }

    public void initializeMapLayers(MapLayer firstLayer) {
        this.firstLayer = firstLayer;
    }

    public void run() {
        if (firstLayer == null) {
            return;
        }

        notifyStateChange(SimulationStatus.INITIALIZING);
        bootstrapSimulation();
        notifyStateChange(SimulationStatus.RUNNING);

        try {
            while (!endRequested) {
                if (pauseRequested) {
                    pauseLatch = new CountDownLatch(1);
                    notifyStateChange(SimulationStatus.PAUSED);
                    pauseLatch.await();
                    notifyStateChange(SimulationStatus.RUNNING);
                }

                day++;
                advanceSimulation();
                Thread.sleep(speedValue == null ? 500 : (int)speedValue.get());
            }
        } catch (InterruptedException ignored) {
        } finally {
            notifyStateChange(SimulationStatus.EXITED);
        }
    }

    public void addMapChangeSubscriber(MapChangeSubscriber subscriber) {
        mapChangeSubscribers.add(subscriber);
    }

    public void addGlobalStatsSubscriber(StatsSubscriber<GlobalStatsEvent> subscriber) {
        globalStatsSubscribers.add(subscriber);
    }

    public void addAnimalStatsSubscriber(StatsSubscriber<AnimalStatsEvent> subscriber) {
        animalStatsSubscribers.add(subscriber);
    }

    public void addOnSimulationStateChanged(Consumer<SimulationStatus> handler) {
        simulationStatusSubscribers.add(handler);
    }

    public void setPauseState(ObservableBooleanValue observableBooleanValue) {
        pauseState = observableBooleanValue;
        pauseState.addListener((var e) -> this.handlePauseStateChange());
    }

    public void setSimulationSpeedValue(ObservableDoubleValue observable) {
        speedValue = observable;
    }

    public void setAnimalToFollow(Animal animal) {
        animalToFollow = animal;
    }

    public void requestEnd() {
        pauseLatch.countDown();
        endRequested = true;
    }

    private void notifyStateChange(SimulationStatus status) {
        for (var handler : simulationStatusSubscribers) {
            handler.accept(status);
        }
    }

    private void handlePauseStateChange() {
        if (pauseState.get()) {
            pauseRequested = true;
        } else {
            pauseRequested = false;
            pauseLatch.countDown();
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
        growGrassPhase.setEatenGrass(cleanupPhase.getEatenGrass());
        growGrassPhase.setBlockedFields(permanentlyBlockedFields);
        visitLayers(growGrassPhase);

        SummaryPhase summaryPhase = new SummaryPhase();
        summaryPhase.setDay(day);
        visitLayers(summaryPhase);
        GlobalStatsEvent globalStatsEvent = summaryPhase.getGlobalStats();

        MapChangeEvent mapChangeEvent = new MapChangeEvent(
            day,
            summaryPhase.getMapBoundary(),
            summaryPhase.getPreferredGrassFields(),
            summaryPhase.getAnimals(),
            summaryPhase.getGrass(),
            summaryPhase.getTunnels(),
            globalStatsEvent.mostPopularGenome()
        );

        for (var subscriber : mapChangeSubscribers) {
            subscriber.onMapChange(mapChangeEvent);
        }
        for (var subscriber : globalStatsSubscribers) {
            subscriber.updateStats(globalStatsEvent);
        }

        if (animalToFollow != null) {
            SingleStatsGenerator singleStatsGenerator = new SingleStatsGenerator(animalToFollow);
            for (var subscriber : animalStatsSubscribers) {
                subscriber.updateStats(singleStatsGenerator.getStats());
            }
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
