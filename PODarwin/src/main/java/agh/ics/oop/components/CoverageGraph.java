package agh.ics.oop.components;

import agh.ics.oop.model.visualization.MapChangeEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

public class CoverageGraph extends VBox implements MapChangeSubscriber {
    private final XYChart.Series<Number, Number> animalSeries;
    private final XYChart.Series<Number, Number> grassSeries;
    private final NumberAxis xAxis;
    private final static int VISIBLE_RANGE = 20;

    public CoverageGraph() {
        animalSeries = new XYChart.Series<>();
        grassSeries = new XYChart.Series<>();

        xAxis = new NumberAxis(0, VISIBLE_RANGE + 2, 1);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false);
        yAxis.setAutoRanging(true);
        yAxis.setAnimated(false);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setLegendVisible(false);
        lineChart.getData().add(animalSeries);
        lineChart.getData().add(grassSeries);

        lineChart.setMaxHeight(200);

        Platform.runLater(() -> {
            getChildren().add(lineChart);
        });
    }

    @Override
    public void onMapChange(MapChangeEvent event) {
        Platform.runLater(() -> {
            if (animalSeries.getData().size() > VISIBLE_RANGE) {
                animalSeries.getData().remove(0);
                grassSeries.getData().remove(0);
                xAxis.setLowerBound(event.day() - VISIBLE_RANGE);
                xAxis.setUpperBound(event.day() + 2);
            }

            animalSeries.getData().add(new XYChart.Data<>(event.day(), event.animals().size()));
            grassSeries.getData().add(new XYChart.Data<>(event.day(), event.grass().size()));
        });
    }
}
