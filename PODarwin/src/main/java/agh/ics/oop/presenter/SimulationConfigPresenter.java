package agh.ics.oop.presenter;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.ReproductionParams;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.WorldLayersBuilder;
import agh.ics.oop.model.world.layers.MapLayer;
import agh.ics.oop.utils.ConfigReader;
import agh.ics.oop.utils.ConfigWriter;
import agh.ics.oop.utils.CsvWriter;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class SimulationConfigPresenter {
    private final List<Consumer<MapLayer>> configSubmittedSubscribers;
    private HashMap<String, Integer> settings = new HashMap<>();
    private File statsFile = null;
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
    @FXML
    public Button saveConfigButton;
    @FXML
    public Button readFileButton;


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
            if (equatorCheckbox.isSelected()) {
                builder.withEquator();
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
            if (statsFile != null) {
                builder.withStatsFile(statsFile);
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

                Node originNode = (Node) event.getSource();
                originNode.getScene().getWindow().hide();
            }
        });
    }

    public void addLayersReadySubscriber(Consumer<MapLayer> subscriber) {
        configSubmittedSubscribers.add(subscriber);
    }

    private void addErrorLabel(Node input, String message) {
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setPadding(new Insets(0, 0, 0, 10));
        ((HBox) input.getParent()).getChildren().add(errorLabel);
        errorLabels.add(errorLabel);
    }

    private void clearErrorLabels() {
        errorLabels.forEach((var node) -> ((HBox) node.getParent()).getChildren().remove(node));
        errorLabels.clear();
    }

    @FXML
    public void saveConfig() {
        clearErrorLabels();
        settings.put("IsAlternating", 0);
        if (alternatingGenomesCheckbox.isSelected()) {
            settings.put("IsAlternating", 1);
        }
        settings.put("IsWrapping", 0);
        if (wrappingWorldCheckbox.isSelected()) {
            settings.put("IsWrapping", 1);
        }
        settings.put("HasEquator", 0);
        if (equatorCheckbox.isSelected()) {
            settings.put("HasEquator", 1);
        }
        settings.put("MapHeight", mapHeightField.getValue());
        settings.put("MapWidth", mapWidthField.getValue());
        settings.put("HolesCount", tunnelsCountField.getValue());
        settings.put("InitGrassCount", initialGrassCountField.getValue());
        settings.put("GrassGrownCount", grassGrownEachIterationField.getValue());
        settings.put("InitEnergyCount", initialAnimalEnergyField.getValue());
        settings.put("GrassEnergy", energyOfGrassField.getValue());
        settings.put("GenomeLength", genomeLengthField.getValue());
        settings.put("InitAnimalCount", initialAnimalsCountField.getValue());
        settings.put("ReproductionEnergyRequired", reproductionEnergyRequiredField.getValue());
        settings.put("ReproductionEnergyThreshold", reproductionEnergyThresholdField.getValue());
        settings.put("MaxMutationsCount", reproductionMutationsMaxField.getValue());
        settings.put("MinMutationsCount", reproductionMutationsMinField.getValue());
        ConfigWriter configWriter = new ConfigWriter();
        configWriter.openSaveConfigDialog(settings);
    }

    @FXML
    public void loadConfig() {
        ConfigReader configReader = new ConfigReader();
        HashMap<String, Integer> config = configReader.readConfig();
        alternatingGenomesCheckbox.setSelected(false);
        if (config.get("IsAlternating") == 1) {
            alternatingGenomesCheckbox.setSelected(true);
        }
        wrappingWorldCheckbox.setSelected(false);
        if (config.get("IsWrapping") == 1) {
            wrappingWorldCheckbox.setSelected(true);
        }
        equatorCheckbox.setSelected(false);
        if (config.get("HasEquator") == 1) {
            equatorCheckbox.setSelected(true);
        }
        mapHeightField.getValueFactory().setValue(config.get("MapHeight"));
        mapWidthField.getValueFactory().setValue(config.get("MapWidth"));
        tunnelsCountField.getValueFactory().setValue(config.get("HolesCount"));
        initialGrassCountField.getValueFactory().setValue(config.get("InitGrassCount"));
        grassGrownEachIterationField.getValueFactory().setValue(config.get("GrassGrownCount"));
        energyOfGrassField.getValueFactory().setValue(config.get("GrassEnergy"));
        genomeLengthField.getValueFactory().setValue(config.get("GenomeLength"));
        initialAnimalsCountField.getValueFactory().setValue(config.get("InitAnimalCount"));
        reproductionEnergyThresholdField.getValueFactory().setValue(config.get("ReproductionEnergyThreshold"));
        reproductionEnergyRequiredField.getValueFactory().setValue(config.get("ReproductionEnergyRequired"));
        reproductionMutationsMinField.getValueFactory().setValue(config.get("MinMutationsCount"));
        reproductionMutationsMaxField.getValueFactory().setValue(config.get("MaxMutationsCount"));
        initialAnimalEnergyField.getValueFactory().setValue(config.get("InitEnergyCount"));
    }

    @FXML
    public void saveStats() {
        CsvWriter csvWriter = new CsvWriter();
        statsFile = csvWriter.openSaveStatsDialog();
    }
}
