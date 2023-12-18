package agh.ics.oop.utils;

import agh.ics.oop.model.Classes.Boundary;
import agh.ics.oop.model.Classes.Vector2D;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HoleIterator implements Iterator<Vector2D>, Iterable<Vector2D> {
    private final List<Vector2D> allPositions;
    private int currentIndex = 0;
    private final int maxWidth;
    private final int maxHeight;
    private final int count;

    public HoleIterator(Boundary boundary, int count) {
        this.maxWidth = boundary.width();
        this.maxHeight = boundary.height();
        this.count = 2 * count;
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
        for (int x = 0; x <= this.maxWidth; x++) {
            for (int y = 0; y <= this.maxHeight; y++) {
                positions.add(new Vector2D(x, y));
            }
        }
        shuffleList(positions);
        return positions;
    }

    private void shuffleList(List<Vector2D> list) {
        Random rand = new Random();
        for (int i = list.size() - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Vector2D temp = list.get(index);
            list.set(index, list.get(i));
            list.set(i, temp);
        }
    }
}
