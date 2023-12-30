package agh.ics.oop.model.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {

    @Test
    void shouldReturnWidth() {
        assertEquals(5, new Boundary(new Vector2D(0, 0), new Vector2D(4, 4)).width());
        assertEquals(2, new Boundary(new Vector2D(1, 2), new Vector2D(2, 6)).width());
        assertEquals(1, new Boundary(new Vector2D(0, 1), new Vector2D(0, 4)).width());
    }

    @Test
    void shouldReturnHeight() {
        assertEquals(5, new Boundary(new Vector2D(-2, 0), new Vector2D(4, 4)).height());
        assertEquals(6, new Boundary(new Vector2D(-2, 1), new Vector2D(4, 6)).height());
        assertEquals(7, new Boundary(new Vector2D(-2, 2), new Vector2D(4, 8)).height());

    }

    @Test
    void shouldReturnNumberOfFields() {
        assertEquals(20, new Boundary(new Vector2D(0, 1), new Vector2D(4, 4)).numberOfFields());
        assertEquals(20, new Boundary(new Vector2D(1, 2), new Vector2D(4, 6)).numberOfFields());
        assertEquals(18, new Boundary(new Vector2D(2, 3), new Vector2D(4, 8)).numberOfFields());
    }
}
