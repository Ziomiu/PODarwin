package agh.ics.oop.components;

import agh.ics.oop.model.visualization.AnimalStatsEvent;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class AnimalStatsNumbers extends VBox implements StatsSubscriber<AnimalStatsEvent> {
    private final Label hintLabel;
    private boolean firstRun = true;
    private final GridPane statsGrid;

    public AnimalStatsNumbers() {
        setPadding(new Insets(10, 10, 10, 10));
        statsGrid = new GridPane();
        hintLabel = new Label("Pause the simulation to select an animal to follow");
        getChildren().addAll(hintLabel, statsGrid);
    }

    @Override
    public void updateStats(AnimalStatsEvent statsEvent) {
        Platform.runLater(() -> {
            if (firstRun && statsEvent != null) {
                firstRun = false;
                ((VBox)hintLabel.getParent()).getChildren().remove(hintLabel);
            }

            buildStatsGrid(statsEvent);
        });
    }

    private void buildStatsGrid(AnimalStatsEvent stats) {
        statsGrid.getChildren().clear();
        if (stats == null) {
            return;
        }

        int i = 0;
        for (Map.Entry<String, String> row : stats.asMap().entrySet()) {
            Label title = new Label(row.getKey());
            title.setStyle("-fx-font-weight: 900");
            Label value = new Label(row.getValue());
            statsGrid.addRow(i, title, value);
            i++;
        }
    }
}
