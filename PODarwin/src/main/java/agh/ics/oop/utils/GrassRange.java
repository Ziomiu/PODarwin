package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;

import java.util.*;

public class GrassRange implements Iterable<Vector2D> {
    private final int max_x;
    private final int max_y;
    private final int min_x;
    private final int min_y;
    private final int count;
    private HashSet<Vector2D> occupied;

    public GrassRange(Boundary boundary, int count) {
        this.max_x = boundary.upper().x();
        this.max_y = boundary.upper().y();
        this.min_x = boundary.lower().x();
        this.min_y = boundary.lower().y();
        this.count = count;
    }

    public GrassRange(Boundary boundary, int count, HashSet<Vector2D> occupied) {
        this(boundary, count);
        this.occupied = occupied;
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
        return positions.subList(0, Math.min(count, positions.size()));
    }

    @Override
    public Iterator<Vector2D> iterator() {
        return new GrassIterator(generateAllPositions());
    }
}
