package agh.ics.oop.model.classes;

import java.util.HashSet;

public record Boundary(Vector2D lower, Vector2D upper) {
    public int width() {
        if (lower.x() == upper.x()) {
            return 1;
        }
        return Math.abs(upper.x() - lower.x()) + 1;
    }

    public int height() {
        if (upper.y() == lower.y()) {
            return 1;
        }
        return Math.abs(upper.y() - lower.y()) + 1;
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

    public HashSet<Vector2D> generateAllPositions() {
        HashSet<Vector2D> positions = new HashSet<>();
        for (int x = lower.x(); x <= upper.x(); x++) {
            for (int y = lower.y(); y <= upper.y(); y++) {
                positions.add(new Vector2D(x, y));
            }
        }
        return positions;
    }

    @Override
    public String toString() {
        return lower + " " + upper;
    }
}
