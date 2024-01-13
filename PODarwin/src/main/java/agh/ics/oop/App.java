package agh.ics.oop;

import agh.ics.oop.presenter.SimulationConfigPresenter;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {
    private SimulationConfigPresenter configPresenter;
    private SimulationPresenter simulationPresenter;
    private Stage primaryStage;
    private final Simulation simulation;

    public App() {
        simulation = new Simulation();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        configurePrimaryStage(primaryStage);
        configureConfigStage();
    }

    private void configurePrimaryStage(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        HBox viewRoot = loader.load();
        simulationPresenter = loader.getController();
        simulationPresenter.getMapChangeSubscribers().forEach(simulation::addMapChangeSubscriber);
        simulationPresenter.getGlobalStatsSubscribers().forEach(simulation::addGlobalStatsSubscriber);
        Scene scene = new Scene(viewRoot);
        primaryStage.setTitle("Simulation");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configureConfigStage() throws Exception {
        Stage configStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("config.fxml"));
        VBox viewRoot = loader.load();
        configPresenter = loader.getController();
        configPresenter.addLayersReadySubscriber(simulation::runOnLayers);
        configStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        configStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        configStage.setScene(new Scene(viewRoot));
        configStage.setTitle("Set up the simulation");
        configStage.initOwner(primaryStage);
        configStage.initModality(Modality.APPLICATION_MODAL);
        configStage.setOnCloseRequest((var e) -> primaryStage.close());
        configStage.show();
    }
}
