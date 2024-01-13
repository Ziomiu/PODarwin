package agh.ics.oop.presenter;

import agh.ics.oop.components.CoverageGraph;
import agh.ics.oop.components.GlobalStatsNumbers;
import agh.ics.oop.components.MapView;
import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.fxml.FXML;

import java.util.List;

public class SimulationPresenter {
    @FXML
    GlobalStatsNumbers globalStatsNumbers;
    @FXML
    CoverageGraph coverageGraph;
    @FXML
    MapView mapView;

    public List<MapChangeSubscriber> getMapChangeSubscribers() {
        return List.of(mapView);
    }

    public List<StatsSubscriber<GlobalStatsEvent>> getGlobalStatsSubscribers() {
        return List.of(globalStatsNumbers);
    }
}
