package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationDirectorPresenter {
    @FXML
    Button addSimulationButton;
    @FXML
    VBox runningSimulationsList;
    @FXML
    Label hintLabel;

    Map<Integer, SimulationHost> simulationHosts;

    ExecutorService executorService;
    int index = 1;

    @FXML
    public void initialize() {
        executorService = Executors.newFixedThreadPool(4);
        simulationHosts = new HashMap<>();
        addSimulationButton.setOnMouseClicked((var e) -> {
            hideHintIfVisible();

            SimulationHost host = addNewSimulationHost();
            try {
                host.bootstrap();
                host.setOnSimulationStart(executorService::submit);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void closeGracefully() {
        simulationHosts.values().forEach(SimulationHost::endSimulation);

        Thread executorShutdownThread = new Thread(() -> {
            try {
                executorService.shutdown();
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException ignored) {
                executorService.shutdownNow();
            }
        });
        executorShutdownThread.start();
    }

    private SimulationHost addNewSimulationHost() {
        SimulationHost simulationHost = new SimulationHost(index);
        Label label = new Label("Simulation %d".formatted(index));
        runningSimulationsList.getChildren().add(label);
        simulationHosts.put(index, simulationHost);
        index++;
        return simulationHost;
    }

    private void hideHintIfVisible() {
        if (hintLabel.getParent() == null) {
            return;
        }

        ((AnchorPane)hintLabel.getParent()).getChildren().remove(hintLabel);
    }
}
