package agh.ics.oop.presenter;

import agh.ics.oop.components.AnimalStatsNumbers;
import agh.ics.oop.components.CoverageGraph;
import agh.ics.oop.components.GlobalStatsNumbers;
import agh.ics.oop.components.MapView;
import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.visualization.AnimalStatsEvent;
import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
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
    Button pauseButton;
    private BooleanProperty pauseState;

    @FXML
    public void initialize() {
        pauseState = new SimpleBooleanProperty(false);
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

    public List<StatsSubscriber<AnimalStatsEvent>> getAnimalChangeSubscribers() {
        return List.of(animalStats);
    }

    public List<MapChangeSubscriber> getMapChangeSubscribers() {
        return List.of(mapView, coverageGraph);
    }

    public List<StatsSubscriber<GlobalStatsEvent>> getGlobalStatsSubscribers() {
        return List.of(globalStatsNumbers);
    }
}
