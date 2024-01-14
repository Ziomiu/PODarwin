package agh.ics.oop.presenter;

import agh.ics.oop.components.CoverageGraph;
import agh.ics.oop.components.GlobalStatsNumbers;
import agh.ics.oop.components.MapView;
import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.List;

public class SimulationPresenter {
    @FXML
    GlobalStatsNumbers globalStatsNumbers;
    @FXML
    CoverageGraph coverageGraph;
    @FXML
    MapView mapView;
    @FXML
    Button pauseButton;
    BooleanProperty pauseState;

    @FXML
    public void initialize() {
        pauseState = new SimpleBooleanProperty(false);
        pauseButton.setOnMouseClicked((var e) -> {
            Platform.runLater(() -> {
                pauseState.set(!pauseState.get());
                pauseButton.setText(pauseState.get() ? "⏵" : "⏸");
            });
        });
    }

    public ObservableBooleanValue getPauseState() {
        return pauseState;
    }

    public List<MapChangeSubscriber> getMapChangeSubscribers() {
        return List.of(mapView, coverageGraph);
    }

    public List<StatsSubscriber<GlobalStatsEvent>> getGlobalStatsSubscribers() {
        return List.of(globalStatsNumbers);
    }
}
