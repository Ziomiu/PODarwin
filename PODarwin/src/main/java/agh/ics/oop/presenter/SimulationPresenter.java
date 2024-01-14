package agh.ics.oop.presenter;

import agh.ics.oop.components.*;
import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.visualization.AnimalStatsEvent;
import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableDoubleValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

public class SimulationPresenter {
    @FXML
    GlobalStatsNumbers globalStatsNumbers;
    @FXML
    CoverageGraph coverageGraph;
    @FXML
    MapView mapView;
    @FXML
    AnimalStatsNumbers animalStats;
    @FXML
    PausedView pausedView;
    @FXML
    Button pauseButton;
    private BooleanProperty pauseState;

    @FXML
    public void initialize() {
        pauseState = new SimpleBooleanProperty(false);
        animalStats.visibleProperty().bind(pauseState.not());
        animalStats.managedProperty().bind(pauseState.not());
        pausedView.visibleProperty().bind(pauseState);
        pausedView.visibleProperty().bind(pauseState);
        registerMapHighlight();
        mapView.setPauseState(pauseState);
        String play = new String("⏵".getBytes(), StandardCharsets.UTF_8);
        String pause = new String("⏸".getBytes(), StandardCharsets.UTF_8);
        pauseButton.setOnMouseClicked((var e) -> {
            Platform.runLater(() -> {
                pauseState.set(!pauseState.get());
                pauseButton.setText(pauseState.get() ? play : pause);
            });
        });
    }

    public void setOnAnimalSelected(Consumer<Animal> handler) {
        mapView.addOnAnimalSelected(handler);
    }

    public ObservableBooleanValue getPauseState() {
        return pauseState;
    }

    public ObservableDoubleValue getSpeedValue() {
        return pausedView.getSpeedValue();
    }

    public List<StatsSubscriber<AnimalStatsEvent>> getAnimalChangeSubscribers() {
        return List.of(animalStats);
    }

    public List<MapChangeSubscriber> getMapChangeSubscribers() {
        return List.of(mapView, coverageGraph);
    }

    public List<StatsSubscriber<GlobalStatsEvent>> getGlobalStatsSubscribers() {
        return List.of(globalStatsNumbers);
    }

    private void registerMapHighlight() {
        var highlightGrassProperty = pausedView.getHighlightGrassProperty();
        highlightGrassProperty.addListener(e -> {
            if (highlightGrassProperty.get()) {
                mapView.highlightPreferredGrassFields();
            } else {
                mapView.clearGrassContext();
            }
        });
        pauseState.addListener(e -> {
            if (pauseState.get()) {
                pausedView.getHighlightGrassProperty().set(false);
            }
        });

        var highlightGenomeProperty = pausedView.getHighlightGenomeProperty();
        highlightGenomeProperty.addListener(e -> {
            if (highlightGenomeProperty.get()) {
                mapView.highlightPreferredGenomeFields();
            } else {
                mapView.clearAnimalContext();
            }
        });
        pauseState.addListener(e -> {
            if (pauseState.get()) {
                pausedView.getHighlightGenomeProperty().set(false);
            }
        });
    }
}
