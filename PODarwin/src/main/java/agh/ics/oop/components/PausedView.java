package agh.ics.oop.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class PausedView extends VBox {
    private final Label infoLabel;
    private final Label sliderLabel;
    private final Slider speedSlider;
    private final Button highlightPreferredGrassButton;
    private final Button highlightPreferredGenomeButton;
    private final BooleanProperty highlightGrassProperty;
    private final BooleanProperty highlightGenomeProperty;

    public PausedView() {
        setPadding(new Insets(10, 10, 10, 10));
        highlightGrassProperty = new SimpleBooleanProperty(false);
        highlightGenomeProperty = new SimpleBooleanProperty(false);
        infoLabel = new Label("You may select an animal to follow, show fields that grass prefers, \nshow most popular genome or adjust simulation speed");
        infoLabel.setPadding(new Insets(0, 0, 10, 0));
        sliderLabel = new Label("Adjust speed [ms]");
        sliderLabel.setPadding(new Insets(10, 0, 10, 0));
        speedSlider = new Slider();
        speedSlider.setMaxWidth(200);
        speedSlider.setMin(100);
        speedSlider.setMax(1000);
        speedSlider.setValue(500);
        speedSlider.setMajorTickUnit(100);
        highlightPreferredGrassButton = new Button("Show preferred fields");
        highlightPreferredGrassButton.setOnMouseClicked(e -> highlightGrassProperty.set(!highlightGrassProperty.get()));
        highlightPreferredGenomeButton = new Button("Show animals with preferred genome");
        highlightPreferredGenomeButton.setOnMouseClicked(e -> highlightGenomeProperty.set(!highlightGenomeProperty.get()));
        getChildren().addAll(infoLabel, highlightPreferredGrassButton, highlightPreferredGenomeButton, sliderLabel, speedSlider);
    }

    public BooleanProperty getHighlightGrassProperty() {
        return highlightGrassProperty;
    }

    public BooleanProperty getHighlightGenomeProperty() {
        return highlightGenomeProperty;
    }

    public ObservableDoubleValue getSpeedValue() {
        return speedSlider.valueProperty();
    }
}
