package agh.ics.oop.components;

import agh.ics.oop.model.visualization.AnimalStatsEvent;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.scene.layout.VBox;

public class AnimalStatsNumbers extends VBox implements StatsSubscriber<AnimalStatsEvent> {
    @Override
    public void updateStats(AnimalStatsEvent statsEvent) {
    }
}
