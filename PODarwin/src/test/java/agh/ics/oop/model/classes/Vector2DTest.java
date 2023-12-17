package agh.ics.oop.model.classes;

import agh.ics.oop.model.Classes.Vector2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2DTest {
    @Test
    void shouldMakeReadableStringRepresentation() {
        assertEquals("(1,2)", (new Vector2D(1, 2)).toString());
        assertEquals("(-1,-2)", (new Vector2D(-1, -2)).toString());
    }

    @Test
    void shouldClassifyPrecedenceBetweenDifferentVectors() {
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(2, 2);
        Vector2D v3 = new Vector2D(2, 3);

        Assertions.assertTrue(v2.follows(v1));
        Assertions.assertTrue(v3.follows(v2));
        Assertions.assertTrue(v3.follows(v1));
        Assertions.assertTrue(v1.precedes(v2));
        Assertions.assertTrue(v1.precedes(v3));
        Assertions.assertTrue(v2.precedes(v3));

        Assertions.assertFalse(v1.follows(v3));
        Assertions.assertFalse(v1.follows(v2));
        Assertions.assertFalse(v2.follows(v3));
        Assertions.assertFalse(v2.precedes(v1));
        Assertions.assertFalse(v3.precedes(v2));
        Assertions.assertFalse(v3.precedes(v1));
    }

    @Test
    void shouldClassifyPrecedenceBetweenIdenticalVectors() {
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(1, 1);

        Assertions.assertTrue(v1.precedes(v2));
        Assertions.assertTrue(v1.follows(v2));
    }

    @Test
    void shouldCompareForEquality() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(1, 2);

        assertEquals(v1, v2);
    }

    @Test
    void shouldAddTwoVectors() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(2, 3);

        assertEquals(new Vector2D(3, 5), v1.add(v2));
    }

    @Test
    void shouldSubtractTwoVectors() {
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(1, 2);

        assertEquals(new Vector2D(1, 1), v1.subtract(v2));
    }

    @Test
    void shouldCalculateUpperRightVector() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(2, 3);

        assertEquals(new Vector2D(2, 3), v1.upperRight(v2));
    }

    @Test
    void shouldCalculateLowerLeftVector() {
        Vector2D v1 = new Vector2D(-1, 2);
        Vector2D v2 = new Vector2D(2, -1);

        assertEquals(new Vector2D(-1, -1), v1.lowerLeft(v2));
    }

    @Test
    void shouldCreateOppositeVector() {
        Vector2D v1 = new Vector2D(3, -4);

        assertEquals(new Vector2D(-3, 4), v1.opposite());
    }

    @Test
    void shouldCompareTwoVectorsXWise() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(2, 1);
        Vector2D v3 = new Vector2D(1, 1);
        Vector2D v4 = new Vector2D(1, 1);

        assertEquals(v1.compareTo(v2), -1);
        assertEquals(v2.compareTo(v1), 1);
        assertEquals(v1.compareTo(v3), 1);
        assertEquals(v3.compareTo(v4), 0);
    }
}