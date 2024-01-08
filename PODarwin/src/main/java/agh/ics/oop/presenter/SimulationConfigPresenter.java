package agh.ics.oop.presenter;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.ReproductionParams;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.WorldLayersBuilder;
import agh.ics.oop.model.world.layers.MapLayer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
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
    public TextField mapWidthField;
    @FXML
    public TextField mapHeightField;
    @FXML
    public TextField tunnelsCountField;
    @FXML
    public TextField initialGrassCountField;
    @FXML
    public TextField grassGrownEachIterationField;
    @FXML
    public TextField initialAnimalEnergyField;
    @FXML
    public TextField energyOfGrassField;
    @FXML
    public TextField genomeLengthField;
    @FXML
    public TextField initialAnimalsCountField;
    @FXML
    public TextField reproductionEnergyThresholdField;
    @FXML
    public TextField reproductionEnergyRequiredField;
    @FXML
    public TextField reproductionMutationsMinField;
    @FXML
    public TextField reproductionMutationsMaxField;
    @FXML
    public Button startValidateButton;

    public SimulationConfigPresenter() {
        configSubmittedSubscribers = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        startValidateButton.setOnMouseClicked(event -> {
            WorldLayersBuilder builder = new WorldLayersBuilder();
            boolean ok = true;
            if (alternatingGenomesCheckbox.isSelected()) {
                builder.withAlternatingGenomes();
            }

            if (wrappingWorldCheckbox.isSelected()) {
                builder.withWrappingWorld();
            }
            if(equatorCheckbox.isSelected()){
                builder.withEquator();
            }

            try {
                builder.withBoundary(new Boundary(
                    new Vector2D(0, 0),
                    new Vector2D(
                        Integer.parseInt(mapWidthField.textProperty().get()),
                        Integer.parseInt(mapHeightField.textProperty().get())
                    )
                ));
            } catch (Exception e) {
                ok = false;
                mapWidthField.textProperty().set("");
                mapHeightField.textProperty().set("");
            }

            try {
                builder.withTunnels(Integer.parseInt(tunnelsCountField.textProperty().get()));
            } catch (Exception e) {
                ok = false;
                tunnelsCountField.textProperty().get();
            }

            try {
                builder.withInitialGrassCount(Integer.parseInt(initialGrassCountField.textProperty().get()));
            } catch (Exception e) {
                ok = false;
                initialGrassCountField.textProperty().get();
            }

            try {
                builder.withGrassGrownEachIteration(Integer.parseInt(grassGrownEachIterationField.textProperty().get()));
            } catch (Exception e) {
                ok = false;
                grassGrownEachIterationField.textProperty().get();
            }

            try {
                builder.withInitialAnimalsEnergy(Integer.parseInt(initialAnimalEnergyField.textProperty().get()));
            } catch (Exception e) {
                ok = false;
                initialAnimalEnergyField.textProperty().get();
            }

            try {
                builder.withEnergyOfGrass(Integer.parseInt(energyOfGrassField.textProperty().get()));
            } catch (Exception e) {
                ok = false;
                energyOfGrassField.textProperty().get();
            }

            try {
                builder.withGenomeLength(Integer.parseInt(genomeLengthField.textProperty().get()));
            } catch (Exception e) {
                ok = false;
                genomeLengthField.textProperty().get();
            }

            try {
                builder.withInitialAnimalCount(Integer.parseInt(initialAnimalsCountField.textProperty().get()));
            } catch (Exception e) {
                ok = false;
                initialAnimalsCountField.textProperty().get();
            }

            try {
                builder.withReproductionParams(new ReproductionParams(
                    Integer.parseInt(reproductionEnergyThresholdField.textProperty().get()),
                    Integer.parseInt(reproductionEnergyRequiredField.textProperty().get()),
                    Integer.parseInt(reproductionMutationsMinField.textProperty().get()),
                    Integer.parseInt(reproductionMutationsMaxField.textProperty().get())
                ));
            } catch (Exception e) {
                ok = false;
                reproductionEnergyThresholdField.textProperty().set("");
                reproductionEnergyRequiredField.textProperty().set("");
                reproductionMutationsMinField.textProperty().set("");
                reproductionMutationsMaxField.textProperty().set("");
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
}
