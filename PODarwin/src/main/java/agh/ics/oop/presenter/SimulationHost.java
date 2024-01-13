package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.world.layers.MapLayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class SimulationHost {
    private SimulationConfigPresenter configPresenter;
    private SimulationPresenter simulationPresenter;
    private final Simulation simulation;
    private Consumer<Runnable> simulationStartHandler;
    private int id;

    public SimulationHost(int id) {
        simulation = new Simulation();
        this.id = id;
    }

    public void setSimulationStartHandler(Consumer<Runnable> handler) {
        simulationStartHandler = handler;
    }

    public void bootstrap() throws Exception {
        Stage simulationStage = getPrimaryStage();
        Stage configStage = getConfigStage(simulationStage);
        simulationStage.show();
        configStage.show();
    }

    private Stage getPrimaryStage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage simulationStage = new Stage();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        HBox viewRoot = loader.load();
        simulationPresenter = loader.getController();

        simulationPresenter.getMapChangeSubscribers().forEach(simulation::addMapChangeSubscriber);
        simulationPresenter.getGlobalStatsSubscribers().forEach(simulation::addGlobalStatsSubscriber);

        Scene scene = new Scene(viewRoot);
        simulationStage.setTitle("Simulation #%d".formatted(id));
        simulationStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        simulationStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        simulationStage.setScene(scene);
        return simulationStage;
    }

    private Stage getConfigStage(Stage simulationStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage configStage = new Stage();
        loader.setLocation(getClass().getClassLoader().getResource("config.fxml"));
        VBox viewRoot = loader.load();
        configPresenter = loader.getController();

        configPresenter.addLayersReadySubscriber((MapLayer firstLayer) -> {
            simulation.initializeMapLayers(firstLayer);
            if (simulationStartHandler != null) {
                simulationStartHandler.accept(simulation);
            }
        });

        configStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        configStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        configStage.setScene(new Scene(viewRoot));
        configStage.setTitle("Set up the simulation");
        configStage.initOwner(simulationStage);
        configStage.initModality(Modality.APPLICATION_MODAL);
        configStage.setOnCloseRequest((var e) -> simulationStage.close());
        return configStage;
    }
}
