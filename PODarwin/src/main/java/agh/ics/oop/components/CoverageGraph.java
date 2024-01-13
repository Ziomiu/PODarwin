package agh.ics.oop.components;

import agh.ics.oop.model.visualization.MapChangeEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CoverageGraph extends VBox implements MapChangeSubscriber {
    public CoverageGraph() {
        getChildren().add(new Label("coverage graph"));
    }

    @Override
    public void onMapChange(MapChangeEvent event) {
    }
}
