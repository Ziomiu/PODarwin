package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationDirectorPresenter {
    @FXML
    Button addSimulationButton;
    @FXML
    VBox runningSimulationsList;

    Map<Integer, SimulationHost> simulationHosts;

    ExecutorService executorService;
    int index = 1;

    @FXML
    public void initialize() {
        executorService = Executors.newFixedThreadPool(4);
        simulationHosts = new HashMap<>();
        addSimulationButton.setOnMouseClicked((var e) -> {
            SimulationHost host = addNewSimulationHost();
            try {
                host.bootstrap();
                host.setSimulationStartHandler(executorService::submit);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private SimulationHost addNewSimulationHost() {
        SimulationHost simulationHost = new SimulationHost(index);
        Label label = new Label("Simulation %d".formatted(index));
        runningSimulationsList.getChildren().add(label);
        simulationHosts.put(index, simulationHost);
        index++;
        return simulationHost;
    }
}
