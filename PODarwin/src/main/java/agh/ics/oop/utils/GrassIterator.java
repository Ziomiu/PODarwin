package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;

import java.util.*;

public class GrassIterator implements Iterator<Vector2D>, Iterable<Vector2D> {
    private final List<Vector2D> allPositions;
    private int currentIndex = 0;
    private final int max_x;
    private final int max_y;
    private final int min_x;
    private final int min_y;
    private final int count;
    private final HashSet<Vector2D> occupied;

    public GrassIterator(Boundary boundary, int count, HashSet<Vector2D> occupied) {
        this.max_x = boundary.upper().x();
        this.max_y = boundary.upper().y();
        this.min_x = boundary.lower().x();
        this.min_y = boundary.lower().y();
        this.count = count;
        this.occupied = occupied;
        this.allPositions = generateAllPositions();
    }

    @Override
    public boolean hasNext() {
        return this.currentIndex < this.count;
    }

    @Override
    public Vector2D next() {
        if (!hasNext()) {
            throw new RuntimeException("No positions left");
        }
        Vector2D position = this.allPositions.get(this.currentIndex);
        this.currentIndex++;
        return position;
    }

    @Override
    public Iterator<Vector2D> iterator() {
        return this;
    }

    private List<Vector2D> generateAllPositions() {
        List<Vector2D> positions = new LinkedList<>();
        for (int x = this.min_x; x <= this.max_x; x++) {
            for (int y = this.min_y; y <= this.max_y; y++) {
                if (!this.occupied.contains(new Vector2D(x, y))) {
                    positions.add(new Vector2D(x, y));
                }
            }
        }
        Collections.shuffle(positions);
        return positions;
    }

}
