package agh.ics.oop.model.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {

    @Test
    void shouldReturnWidth() {
        assertEquals(6, new Boundary(new Vector2D(-2, -2), new Vector2D(4, 4)).width());
        assertEquals(4, new Boundary(new Vector2D(-2, -2), new Vector2D(2, 6)).width());
        assertEquals(2, new Boundary(new Vector2D(-2, -2), new Vector2D(0, 4)).width());
    }

    @Test
    void shouldReturnHeight() {
        assertEquals(6, new Boundary(new Vector2D(-2, -2), new Vector2D(4, 4)).height());
        assertEquals(8, new Boundary(new Vector2D(-2, -2), new Vector2D(4, 6)).height());
        assertEquals(10, new Boundary(new Vector2D(-2, -2), new Vector2D(4, 8)).height());

    }

    @Test
    void shouldReturnNumberOfFields() {
        assertEquals(36, new Boundary(new Vector2D(-2, -2), new Vector2D(4, 4)).numberOfFields());
        assertEquals(48, new Boundary(new Vector2D(-2, -2), new Vector2D(4, 6)).numberOfFields());
        assertEquals(60, new Boundary(new Vector2D(-2, -2), new Vector2D(4, 8)).numberOfFields());
    }
}