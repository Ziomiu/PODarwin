package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Vector2D;

import java.util.*;

public class PositionIterator implements Iterator<Vector2D> {
    private final List<Vector2D> availablePositions;
    private int currentIndex = 0;

    public PositionIterator(List<Vector2D> availablePositions) {
        this.availablePositions = availablePositions;
    }

    @Override
    public boolean hasNext() {
        return this.currentIndex < this.availablePositions.size();
    }

    @Override
    public Vector2D next() {
        if (!hasNext()) {
            throw new RuntimeException("No positions left");
        }
        Vector2D position = this.availablePositions.get(this.currentIndex);
        this.currentIndex++;
        return position;
    }
}
