package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.enums.SimulationStatus;
import agh.ics.oop.model.world.layers.MapLayer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class SimulationHost {
    private final Stage configStage;
    private final Stage simulationStage;
    private final Simulation simulation;
    private Consumer<Runnable> simulationStartHandler;
    private List<Consumer<SimulationStatus>> simulationStateChangeSubscribers;
    private final int id;

    public SimulationHost(int id) {
        configStage = new Stage();
        simulationStage = new Stage();
        simulation = new Simulation();
        simulationStateChangeSubscribers = new LinkedList<>();
        this.id = id;
    }

    public void setOnSimulationStart(Consumer<Runnable> handler) {
        simulationStartHandler = handler;
    }

    public void addOnSimulationStateChanged(Consumer<SimulationStatus> handler) {
        simulationStateChangeSubscribers.add(handler);
        simulation.addOnSimulationStateChanged(handler);
    }

    public void bootstrap() throws Exception {
        Stage simulationStage = getPrimaryStage();
        Stage configStage = getConfigStage(simulationStage);
        simulationStage.show();
        configStage.show();
    }

    public void endSimulation() {
        Platform.runLater(() -> {
            if (configStage.isShowing()) {
                configStage.close();
            }
            if (simulationStage.isShowing()) {
                simulationStage.close();
            }
        });

        simulation.requestEnd();
    }

    private Stage getPrimaryStage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        HBox viewRoot = loader.load();
        SimulationPresenter simulationPresenter = loader.getController();

        simulation.setPauseState(simulationPresenter.getPauseState());
        simulationPresenter.getMapChangeSubscribers().forEach(simulation::addMapChangeSubscriber);
        simulationPresenter.getGlobalStatsSubscribers().forEach(simulation::addGlobalStatsSubscriber);
        simulationPresenter.getAnimalChangeSubscribers().forEach(simulation::addAnimalStatsSubscriber);
        simulationPresenter.setOnAnimalSelected(simulation::setAnimalToFollow);
        simulation.setSimulationSpeedValue(simulationPresenter.getSpeedValue());

        Scene scene = new Scene(viewRoot);
        simulationStage.setTitle("Simulation #%d".formatted(id));
        simulationStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        simulationStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        simulationStage.setScene(scene);
        simulationStage.setOnCloseRequest((var e) -> simulation.requestEnd());
        return simulationStage;
    }

    private Stage getConfigStage(Stage simulationStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("config.fxml"));
        VBox viewRoot = loader.load();
        SimulationConfigPresenter configPresenter = loader.getController();
        configPresenter.addLayersReadySubscriber(this::acceptLayersReadyHandler);

        configStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        configStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        configStage.setScene(new Scene(viewRoot));
        configStage.setTitle("Set up the simulation");
        configStage.initOwner(simulationStage);
        configStage.initModality(Modality.WINDOW_MODAL);
        configStage.setOnCloseRequest((var e) -> {
            for (var handler : simulationStateChangeSubscribers) {
                handler.accept(SimulationStatus.ABORTED);
            }
            Platform.runLater(simulationStage::close);
        });
        return configStage;
    }

    private void acceptLayersReadyHandler(MapLayer layer) {
        simulation.initializeMapLayers(layer);
        if (simulationStartHandler != null) {
            simulationStartHandler.accept(simulation);
        }
    }
}
