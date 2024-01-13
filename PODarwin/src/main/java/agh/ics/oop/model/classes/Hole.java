package agh.ics.oop.model.classes;

import javafx.scene.paint.Color;

public class Hole implements WorldElement, Drawable {
    private final Vector2D position;
    private final Vector2D outPosition;

    public Hole(Vector2D position, Vector2D outPosition) {
        this.position = position;
        this.outPosition = outPosition;
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getOutPosition() {
        return outPosition;
    }
}
