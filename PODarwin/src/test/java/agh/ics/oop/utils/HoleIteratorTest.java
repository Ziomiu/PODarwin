package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class HoleIteratorTest {
    @Test
    public void shouldGenerateUniqueValues() {
        ArrayList<Vector2D> positions = new ArrayList<>();
        HoleIterator holes = new HoleIterator(new Boundary(new Vector2D(0, 0), new Vector2D(5, 5)), 5);
        for (Vector2D position : holes) {
            positions.add(position);
        }
        assertEquals(positions.size(), new HashSet<>(positions).size());
    }

    @Test
    public void shouldGenerateTwiceAsMany() {
        ArrayList<Vector2D> positions = new ArrayList<>();
        HoleIterator holes = new HoleIterator(new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)), 6);
        for (Vector2D position : holes) {
            positions.add(position);
        }
        assertEquals(12, positions.size());
    }

    @Test
    public void shouldGenerateBoundries() {
        ArrayList<Vector2D> positions = new ArrayList<>();
        HoleIterator holes = new HoleIterator(new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)), 6);
        for (Vector2D position : holes) {
            assertTrue((position.x() >= 0 && position.x() <= 10 && position.y() >= 0 && position.y() <= 10));
        }

    }

    @Test
    public void shouldThrowWhenNoNext() {
        assertThrows(
                RuntimeException.class,
                () -> new HoleIterator(new Boundary(new Vector2D((int) Math.random(), (int) Math.random()), new Vector2D((int) Math.random(), (int) Math.random())), 0).next()
        );
    }

}