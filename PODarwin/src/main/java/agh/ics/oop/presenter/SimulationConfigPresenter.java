package agh.ics.oop.presenter;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.ReproductionParams;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.WorldLayersBuilder;
import agh.ics.oop.model.world.layers.MapLayer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class SimulationConfigPresenter {
    private final List<Consumer<MapLayer>> configSubmittedSubscribers;

    @FXML
    public CheckBox alternatingGenomesCheckbox;
    @FXML
    public CheckBox wrappingWorldCheckbox;
    @FXML
    public CheckBox equatorCheckbox;
    @FXML
    public Spinner<Integer> mapWidthField;
    @FXML
    public Spinner<Integer> mapHeightField;
    @FXML
    public Spinner<Integer> tunnelsCountField;
    @FXML
    public Spinner<Integer> initialGrassCountField;
    @FXML
    public Spinner<Integer> grassGrownEachIterationField;
    @FXML
    public Spinner<Integer> initialAnimalEnergyField;
    @FXML
    public Spinner<Integer> energyOfGrassField;
    @FXML
    public Spinner<Integer> genomeLengthField;
    @FXML
    public Spinner<Integer> initialAnimalsCountField;
    @FXML
    public Spinner<Integer> reproductionEnergyThresholdField;
    @FXML
    public Spinner<Integer> reproductionEnergyRequiredField;
    @FXML
    public Spinner<Integer> reproductionMutationsMinField;
    @FXML
    public Spinner<Integer> reproductionMutationsMaxField;
    @FXML
    public Button startValidateButton;

    List<Node> errorLabels;

    public SimulationConfigPresenter() {
        configSubmittedSubscribers = new ArrayList<>();
        errorLabels = new LinkedList<>();
    }

    @FXML
    public void initialize() {
        startValidateButton.setOnMouseClicked(event -> {
            clearErrorLabels();

            WorldLayersBuilder builder = new WorldLayersBuilder();
            boolean ok = true;

            if (alternatingGenomesCheckbox.isSelected()) {
                builder.withAlternatingGenomes();
            }

            if (wrappingWorldCheckbox.isSelected()) {
                builder.withWrappingWorld();
            }

            Boundary worldBoundary = new Boundary(
                new Vector2D(0, 0),
                new Vector2D(
                    mapWidthField.getValue(),
                    mapHeightField.getValue()
                )
            );
            builder.withBoundary(worldBoundary);

            int fieldsLeft = worldBoundary.numberOfFields();
            if (tunnelsCountField.getValue() > fieldsLeft) {
                addErrorLabel(tunnelsCountField, "Not enough fields");
                ok = false;
            } else if (ok) {
                builder.withTunnels(tunnelsCountField.getValue());
                fieldsLeft -= tunnelsCountField.getValue();
            }

            if (initialGrassCountField.getValue() > fieldsLeft) {
                addErrorLabel(initialGrassCountField, "Not enough fields");
                ok = false;
            } else if (ok) {
                builder.withInitialGrassCount(initialGrassCountField.getValue());
                initialGrassCountField.getValue();
            }

            builder
                .withGrassGrownEachIteration(grassGrownEachIterationField.getValue())
                .withInitialAnimalsEnergy(initialAnimalEnergyField.getValue())
                .withEnergyOfGrass(energyOfGrassField.getValue())
                .withGenomeLength(genomeLengthField.getValue())
                .withInitialAnimalCount(initialAnimalsCountField.getValue());

            if (reproductionMutationsMaxField.getValue() < reproductionMutationsMinField.getValue()) {
                addErrorLabel(reproductionMutationsMaxField, "Max < Min");
                ok = false;
            } else if (ok) {
                builder.withReproductionParams(new ReproductionParams(
                    reproductionEnergyThresholdField.getValue(),
                    reproductionEnergyRequiredField.getValue(),
                    reproductionMutationsMinField.getValue(),
                    reproductionMutationsMaxField.getValue()
                ));
            }

            if (ok) {
                MapLayer layer = builder.build();
                for (var subscriber : configSubmittedSubscribers) {
                    subscriber.accept(layer);
                }

                Node originNode = (Node)event.getSource();
                originNode.getScene().getWindow().hide();
            }
        });
    }

    public void addConfigSubmittedSubscriber(Consumer<MapLayer> subscriber) {
        configSubmittedSubscribers.add(subscriber);
    }

    private void addErrorLabel(Node input, String message) {
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setPadding(new Insets(0, 0, 0, 10));
        ((HBox)input.getParent()).getChildren().add(errorLabel);
        errorLabels.add(errorLabel);
    }

    private void clearErrorLabels() {
        errorLabels.forEach((var node) -> ((HBox)node.getParent()).getChildren().remove(node));
        errorLabels.clear();
    }
}
