package agh.ics.oop.components;

import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class GlobalStatsNumbers extends FlowPane implements StatsSubscriber<GlobalStatsEvent> {
    private final Label totalGrassLabel;
    private final Label totalAnimalsLabel;
    private final Label placesLeft;
    private final Label mostPopularGenomeLabel;
    private final Label totalTunnelsLabel;
    private final Label averageLifetimeLabel;
    private final Label averageChildrenLabel;

    public GlobalStatsNumbers() {
        totalGrassLabel = new Label("0");
        totalAnimalsLabel = new Label("0");
        placesLeft = new Label("0");
        mostPopularGenomeLabel = new Label("ABCABC");
        totalTunnelsLabel = new Label("0");
        averageLifetimeLabel = new Label("0.0");
        averageChildrenLabel = new Label("0.0");

        setHgap(8);
        setVgap(8);

        getChildren().addAll(
            getVBoxed(totalGrassLabel, "grass patches"),
            getVBoxed(totalTunnelsLabel, "tunnels"),
            getVBoxed(placesLeft, "places left"),
            getVBoxed(totalAnimalsLabel, "animals"),
            getVBoxed(mostPopularGenomeLabel, "most popular genome"),
            getVBoxed(averageLifetimeLabel, "avg lifetime"),
            getVBoxed(averageChildrenLabel, "avg # of children")
        );
    }

    private VBox getVBoxed(Label label, String title) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 1em; -fx-padding: 0.5em");
        label.setStyle("-fx-font-size: 2em;");
        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        box.getChildren().addAll(label, titleLabel);
        return box;
    }

    @Override
    public void updateStats(GlobalStatsEvent statsEvent) {
        totalGrassLabel.setText(String.valueOf(statsEvent.totalGrass()));
        totalAnimalsLabel.setText(String.valueOf(statsEvent.totalAnimals()));
        placesLeft.setText(String.valueOf(statsEvent.placesLeft()));
        mostPopularGenomeLabel.setText(statsEvent.mostPopularGenome());
        totalTunnelsLabel.setText(String.valueOf(statsEvent.totalTunnels()));
        averageLifetimeLabel.setText(String.valueOf(statsEvent.averageLifetime()));
        averageChildrenLabel.setText(String.valueOf(statsEvent.averageChildren()));
    }
}
