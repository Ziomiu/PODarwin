package agh.ics.oop.presenter;

import agh.ics.oop.components.CoverageGraph;
import agh.ics.oop.components.GlobalStatsNumbers;
import javafx.fxml.FXML;

public class SimulationPresenter {
    @FXML
    GlobalStatsNumbers globalStatsNumbers;
    @FXML
    CoverageGraph coverageGraph;

    @FXML
    public void initialize() {
        System.out.println("initialize");
    }
}
