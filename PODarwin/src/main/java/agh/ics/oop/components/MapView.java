package agh.ics.oop.components;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.visualization.MapChangeEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import agh.ics.oop.utils.AnimalComparator;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

public class MapView extends StackPane implements MapChangeSubscriber {
    private GraphicsContext context;
    private Set<Animal> animals;
    private HashMap<Vector2D, ArrayList<Animal>> animalsByPosition;
    private Consumer<Animal> animalSelectedEventHandler;
    int side = 1;

    public MapView() {
        animalsByPosition = new HashMap<>();
    }

    public void addOnAnimalSelected(Consumer<Animal> handler) {
        animalSelectedEventHandler = handler;
    }

    @Override
    public void onMapChange(MapChangeEvent event) {
        Platform.runLater(() -> {
            if (context == null) {
                prepareCanvas(event.worldBoundary());
            }

            animals = event.animals();
            if (animalsByPosition != null) {
                animalsByPosition.clear();
            }

            context.setFill(Color.web("#cfdfa5"));
            context.fillRect(0, 0, 600, 600);
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
                context.setFill(animal.getColor());
                context.fillRect(animal.getPosition().x() * side, animal.getPosition().y() * side, side, side);
            }
        });
    }

    private void prepareCanvas(Boundary boundary) {
        Canvas canvas;
        if (boundary.width() > boundary.height()) {
            side = 600 / boundary.width();
            canvas = new Canvas(600, side * boundary.height());
        } else {
            side = 600 / boundary.height();
            canvas = new Canvas(boundary.width() * side, 600);
        }
        canvas.setOnMouseClicked(this::mouseClickHandler);
        getChildren().add(canvas);
        context = canvas.getGraphicsContext2D();
    }

    private void mouseClickHandler(MouseEvent event) {
        if (animals.isEmpty() || animalSelectedEventHandler == null) {
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
        animalSelectedEventHandler.accept(animalsOnPosition.get(0));
    }

    private void fillAnimalsByPosition() {
        for (var animal : animals) {
            animalsByPosition.getOrDefault(animal.getPosition(), new ArrayList<>()).add(animal);
        }
    }
}
