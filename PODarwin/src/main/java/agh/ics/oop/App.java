package agh.ics.oop;

import agh.ics.oop.presenter.SimulationDirectorPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("director.fxml"));
        AnchorPane viewRoot = loader.load();
        Scene scene = new Scene(viewRoot);
        SimulationDirectorPresenter presenter = loader.getController();
        primaryStage.setTitle("Simulation Director");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> presenter.closeGracefully());
        primaryStage.show();
    }
}
