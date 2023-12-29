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

    public boolean contains(Vector2D position) {
        return containsHorizontal(position) && containsVertical(position);
    }

    public boolean containsHorizontal(Vector2D position) {
        return position.x() >= lower.x() && position.x() <= upper.x();
    }

    public boolean containsVertical(Vector2D position) {
        return position.y() >= lower.y() && position.y() <= upper.y();
    }
}
