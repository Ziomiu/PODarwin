package agh.ics.oop.components;

import agh.ics.oop.model.visualization.AnimalStatsEvent;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AnimalStatsNumbers extends VBox implements StatsSubscriber<AnimalStatsEvent> {
    private final Label hintLabel;

    public AnimalStatsNumbers() {
        hintLabel = new Label("Pause the simulation to select an animal to follow");
        hintLabel.setAlignment(Pos.CENTER);
        getChildren().add(hintLabel);
    }

    @Override
    public void updateStats(AnimalStatsEvent statsEvent) {
        if (hintLabel.getParent() != null) {
            ((VBox)hintLabel.getParent()).getChildren().remove(hintLabel);
        }

        // todo
    }
}
