package agh.ics.oop;

import agh.ics.oop.presenter.SimulationConfigPresenter;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    private SimulationConfigPresenter configPresenter;
    private SimulationPresenter simulationPresenter;
    private final Simulation simulation;

    public App() {
        simulation = new Simulation();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        configurePrimaryStage(primaryStage);
        configureConfigWindow();
    }

    private void configurePrimaryStage(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        simulationPresenter = loader.getController();
        Scene scene = new Scene(viewRoot);

        primaryStage.setTitle("Simulation");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configureConfigWindow() throws Exception {
        Stage configStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("config.fxml"));
        GridPane viewRoot = loader.load();
        configPresenter = loader.getController();
        configPresenter.addConfigSubmittedSubscriber(simulation::runOnLayers);
        configStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        configStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        configStage.setScene(new Scene(viewRoot));
        configStage.setTitle("Set up the simulation");
        configStage.show();
    }
}
