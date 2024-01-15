package agh.ics.oop.components;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.model.visualization.MapChangeEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.utils.AnimalComparator;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

public class MapView extends StackPane implements MapChangeSubscriber {
    private GraphicsContext context;
    private GraphicsContext grassHelperContext;
    private GraphicsContext animalHelperContext;
    private Set<Animal> animals;
    private HashMap<Vector2D, ArrayList<Animal>> animalsByPosition;
    private Consumer<Animal> animalSelectedEventHandler;
    int canvasSide = 600;
    int side = 1;
    ObservableBooleanValue pauseState;
    Animal selectedAnimal;
    Boundary preferredGrassFields;
    String preferredGenome;

    public MapView() {
        pauseState = new SimpleBooleanProperty(false);
        animalsByPosition = new HashMap<>();
    }

    public void addOnAnimalSelected(Consumer<Animal> handler) {
        animalSelectedEventHandler = handler;
    }

    public void setPauseState(ObservableBooleanValue pauseState) {
        this.pauseState = pauseState;
    }

    public void highlightPreferredGrassFields() {
        if (preferredGrassFields == null) {
            return;
        }

        grassHelperContext.setFill(Color.web("magenta", 0.5));
        grassHelperContext.fillRect(
            preferredGrassFields.lower().x() * side,
            preferredGrassFields.lower().y() * side,
            preferredGrassFields.width() * side,
            preferredGrassFields.height() * side
        );
    }

    public void highlightPreferredGenomeFields() {
        animalHelperContext.setFill(Color.web("#ff0", 0.7));
        for (var animal : animals) {
            var animalGenome = String.join(
                "",
                animal.getGenomeSequence().getAllGenomes().stream().map(Genome::ordinal).map(String::valueOf).toList()
            );

            if (!animalGenome.equals(preferredGenome)) {
                continue;
            }

            animalHelperContext.fillRect(
                animal.getPosition().x() * side,
                animal.getPosition().y() * side,
                side,
                side
            );
        }
    }

    public void clearGrassContext() {
        grassHelperContext.clearRect(0, 0, canvasSide, canvasSide);
    }

    public void clearAnimalContext() {
        animalHelperContext.clearRect(0, 0, canvasSide, canvasSide);
    }

    @Override
    public void onMapChange(MapChangeEvent event) {
        Platform.runLater(() -> {
            if (context == null) {
                context = makeGraphicsContext(event.worldBoundary());
                grassHelperContext = makeGraphicsContext(event.worldBoundary());
                animalHelperContext = makeGraphicsContext(event.worldBoundary());
            }

            preferredGrassFields = event.preferredGrassFields();
            preferredGenome = event.mostPopularGenome();

            context.setFill(Color.web("#cfdfa5"));
            context.fillRect(0, 0, canvasSide, canvasSide);
            animals = event.animals();
            if (animalsByPosition != null) {
                animalsByPosition.clear();
            }

            clearGrassContext();
            clearAnimalContext();
            for (var grass : event.grass()) {
                context.setFill(grass.getColor());
                context.fillRect(grass.getPosition().x() * side, grass.getPosition().y() * side, side, side);
            }
            for (var tunnel : event.tunnels()) {
                context.setFill(tunnel.getColor());
                context.fillRect(tunnel.getPosition().x() * side, tunnel.getPosition().y() * side, side, side);
                context.fillRect(tunnel.getOutPosition().x() * side, tunnel.getOutPosition().y() * side, side, side);
            }
            for (var animal : event.animals()) {
                paintSelectedAnimal(animal);
            }
        });
    }

    private GraphicsContext makeGraphicsContext(Boundary boundary) {
        Canvas canvas;
        if (boundary.width() > boundary.height()) {
            side = canvasSide / boundary.width();
            canvasSide = side * boundary.width(); // counter rounding errors
            canvas = new Canvas(canvasSide, side * boundary.height());
        } else {
            side = canvasSide / boundary.height();
            canvasSide = side * boundary.height(); // counter rounding errors
            canvas = new Canvas(boundary.width() * side, canvasSide);
        }
        canvas.setOnMouseClicked(this::mouseClickHandler);
        getChildren().add(canvas);
        return canvas.getGraphicsContext2D();
    }

    private void mouseClickHandler(MouseEvent event) {
        if (animals.isEmpty() || animalSelectedEventHandler == null || !pauseState.get()) {
            return;
        }

        if (animalsByPosition.isEmpty()) {
            fillAnimalsByPosition();
        }

        Vector2D approximatedVector = new Vector2D((int)event.getX() / side, (int)event.getY() / side);
        if (!animalsByPosition.containsKey(approximatedVector)) {
            return;
        }

        var animalsOnPosition = animalsByPosition.get(approximatedVector);
        animalsOnPosition.sort(new AnimalComparator());
        if (animals.size() > 1) {
            // avoid race condition in painting over the same animal
            restoreCanvasUnderAnimal(selectedAnimal);
        }
        selectedAnimal = animalsOnPosition.get(0);
        paintSelectedAnimal(selectedAnimal, Color.MAGENTA);
        animalSelectedEventHandler.accept(animalsOnPosition.get(0));
    }

    private void fillAnimalsByPosition() {
        for (var animal : animals) {
            var listByPosition = animalsByPosition.getOrDefault(animal.getPosition(), new ArrayList<>());
            listByPosition.add(animal);
            animalsByPosition.put(animal.getPosition(), listByPosition);
        }
    }

    private void paintSelectedAnimal(Animal animal) {
        paintSelectedAnimal(animal, animal.getColor());
    }

    private void paintSelectedAnimal(Animal animal, Color color) {
        context.setFill(color);
        context.fillRect(animal.getPosition().x() * side, animal.getPosition().y() * side, side, side);
    }

    private void restoreCanvasUnderAnimal(Animal animal) {
        if (animal == null) {
            return;
        }

        Platform.runLater(() -> {
            context.setFill(animal.getColor());
            context.fillRect(animal.getPosition().x() * side, animal.getPosition().y() * side, side, side);
        });
    }
}
