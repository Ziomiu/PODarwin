package agh.ics.oop.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.util.Map;

public class PausedView extends VBox {
    private final Label infoLabel;
    private final Label sliderLabel;
    private final Slider speedSlider;
    private final Button highlightButton;
    private final BooleanProperty highlightProperty;

    public PausedView() {
        setPadding(new Insets(10, 10, 10, 10));
        highlightProperty = new SimpleBooleanProperty(false);
        infoLabel = new Label("You may select an animal to follow, show fields that grass prefers, \n or adjust simulation speed");
        infoLabel.setPadding(new Insets(0, 0, 10, 0));
        sliderLabel = new Label("Adjust speed [ms]");
        sliderLabel.setPadding(new Insets(10, 0, 10, 0));
        speedSlider = new Slider();
        speedSlider.setMaxWidth(200);
        speedSlider.setMin(100);
        speedSlider.setMax(1000);
        speedSlider.setValue(500);
        speedSlider.setMajorTickUnit(100);
        highlightButton = new Button("Show preferred fields");
        highlightButton.setOnMouseClicked(e -> highlightProperty.set(!highlightProperty.get()));
        getChildren().addAll(infoLabel, highlightButton, sliderLabel, speedSlider);
    }

    public BooleanProperty getHighlightProperty() {
        return highlightProperty;
    }

    public ObservableDoubleValue getSpeedValue() {
        return speedSlider.valueProperty();
    }
}
