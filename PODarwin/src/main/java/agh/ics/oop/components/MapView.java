package agh.ics.oop.components;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.visualization.MapChangeEvent;
import agh.ics.oop.model.visualization.MapChangeSubscriber;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class MapView extends Pane implements MapChangeSubscriber {
    private GraphicsContext context;
    int side = 1;

    @Override
    public void onMapChange(MapChangeEvent event) {
        if (context == null) {
            prepareCanvas(event.worldBoundary());
        }

        context.clearRect(0, 0, 600, 600);
        for (var grass : event.grass()) {
            context.setFill(grass.getColor());
            context.fillRect(grass.getPosition().x(), grass.getPosition().y(), side, side);
        }
        for (var tunnel : event.tunnels()) {
            context.setFill(tunnel.getColor());
            context.fillRect(tunnel.getPosition().x(), tunnel.getPosition().y(), side, side);
            context.fillRect(tunnel.getOutPosition().x(), tunnel.getOutPosition().y(), side, side);
        }
        for (var animal : event.animals()) {
            context.setFill(animal.getColor());
            context.fillRect(animal.getPosition().x(), animal.getPosition().y(), side, side);
        }
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
        getChildren().add(canvas);
        context = canvas.getGraphicsContext2D();
    }
}
