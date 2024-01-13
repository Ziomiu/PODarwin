package agh.ics.oop.model.classes;

import javafx.scene.paint.Color;

public class Grass implements WorldElement, Drawable {
    private final Vector2D position;
    private final int energy;

    public Grass(Vector2D position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "* " + this.position;
    }
}
