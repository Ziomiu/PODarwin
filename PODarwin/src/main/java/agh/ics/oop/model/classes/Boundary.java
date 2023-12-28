package agh.ics.oop.model.classes;

public record Boundary(Vector2D lower, Vector2D upper) {

    public int width() {
        return Math.abs(upper.x() - lower.x());
    }

    public int height() {
        return Math.abs(upper.y() - lower.y());
    }

    public int numberOfFields() {
        return width() * height();
    }
}