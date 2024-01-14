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
        pauseButton.setOnMouseClicked((var e) -> {
            Platform.runLater(() -> {
                pauseState.set(!pauseState.get());
                pauseButton.setText(pauseState.get() ? "⏵" : "⏸");
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
        var highlightProperty = pausedView.getHighlightProperty();
        highlightProperty.addListener(e -> {
            if (highlightProperty.get()) {
                mapView.highlightPreferredGrassFields();
            } else {
                mapView.clearHighlight();
            }
        });
        pauseState.addListener(e -> {
            if (pauseState.get()) {
                pausedView.getHighlightProperty().set(false);
            }
        });
    }
}
