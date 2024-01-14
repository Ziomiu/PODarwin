package agh.ics.oop.components;

import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.visualization.StatsSubscriber;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class GlobalStatsNumbers extends FlowPane implements StatsSubscriber<GlobalStatsEvent> {
    private final Label totalGrassLabel;
    private final Label totalTunnelsLabel;
    private final Label totalAnimalsLabel;
    private final Label placesLeft;
    private final Label averageLifetimeLabel;
    private final Label averageChildrenLabel;
    private final Label averageEnergyLabel;
    private final Label dayLabel;
    private final Label mostPopularGenomeLabel;

    public GlobalStatsNumbers() {
        totalGrassLabel = new Label("0");
        totalGrassLabel.setPrefWidth(96);
        totalTunnelsLabel = new Label("0");
        totalTunnelsLabel.setPrefWidth(88);
        placesLeft = new Label("0");
        placesLeft.setPrefWidth(88);
        totalAnimalsLabel = new Label("0");
        totalAnimalsLabel.setPrefWidth(88);

        averageLifetimeLabel = new Label("0.0");
        averageLifetimeLabel.setPrefWidth(126);
        averageChildrenLabel = new Label("0.0");
        averageChildrenLabel.setPrefWidth(128);
        averageEnergyLabel = new Label("0.0");
        averageEnergyLabel.setPrefWidth(126);

        dayLabel = new Label("0");
        dayLabel.setPrefWidth(80);
        mostPopularGenomeLabel = new Label();
        mostPopularGenomeLabel.setPrefWidth(324);
        mostPopularGenomeLabel.setTextOverrun(OverrunStyle.ELLIPSIS);

        setAlignment(Pos.CENTER);
        setHgap(8);
        setVgap(8);

        getChildren().addAll(
            getVBoxed(totalGrassLabel, "grass patches"),
            getVBoxed(totalTunnelsLabel, "tunnels"),
            getVBoxed(placesLeft, "places left"),
            getVBoxed(totalAnimalsLabel, "animals"),
            getVBoxed(averageLifetimeLabel, "avg lifetime"),
            getVBoxed(averageChildrenLabel, "avg # of children"),
            getVBoxed(averageEnergyLabel, "avg energy"),
            getVBoxed(dayLabel, "day"),
            getVBoxed(mostPopularGenomeLabel, "most popular genome")
        );
    }

    private VBox getVBoxed(Label label, String title) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #d9d9d9; -fx-background-radius: 1em; -fx-padding: 0.5em");
        label.setStyle("-fx-font-size: 2em; -fx-alignment: center;");
        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        Platform.runLater(() -> {
            box.getChildren().addAll(label, titleLabel);
        });
        return box;
    }

    @Override
    public void updateStats(GlobalStatsEvent statsEvent) {
        Platform.runLater(() -> {
            totalGrassLabel.setText(String.valueOf(statsEvent.totalGrass()));
            totalAnimalsLabel.setText(String.valueOf(statsEvent.totalAnimals()));
            placesLeft.setText(String.valueOf(statsEvent.placesLeft()));
            totalTunnelsLabel.setText(String.valueOf(statsEvent.totalTunnels()));
            averageLifetimeLabel.setText(String.valueOf(statsEvent.averageLifetime()));
            averageChildrenLabel.setText(String.valueOf(statsEvent.averageChildren()));
            averageEnergyLabel.setText(String.valueOf(statsEvent.averageEnergy()));
            dayLabel.setText(String.valueOf(statsEvent.day()));
            mostPopularGenomeLabel.setText(statsEvent.mostPopularGenome());
        });
    }
}
