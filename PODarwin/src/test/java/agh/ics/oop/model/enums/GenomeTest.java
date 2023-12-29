package agh.ics.oop.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenomeTest {
    @Test
    public void shouldRotate() {
        assertEquals(Genome.NORTH_EAST, Genome.NORTH.applyRotation(Genome.NORTH_EAST));
        assertEquals(Genome.EAST, Genome.NORTH.applyRotation(Genome.EAST));
        assertEquals(Genome.SOUTH_EAST, Genome.NORTH.applyRotation(Genome.SOUTH_EAST));
        assertEquals(Genome.SOUTH, Genome.NORTH.applyRotation(Genome.SOUTH));
        assertEquals(Genome.SOUTH_WEST, Genome.NORTH.applyRotation(Genome.SOUTH_WEST));
        assertEquals(Genome.WEST, Genome.NORTH.applyRotation(Genome.WEST));
        assertEquals(Genome.NORTH_WEST, Genome.NORTH.applyRotation(Genome.NORTH_WEST));

        assertEquals(Genome.NORTH_WEST, Genome.SOUTH_EAST.applyRotation(Genome.SOUTH));
        assertEquals(Genome.NORTH_EAST, Genome.SOUTH_WEST.applyRotation(Genome.SOUTH));
        assertEquals(Genome.NORTH_WEST, Genome.NORTH_WEST.applyRotation(Genome.NORTH));
        assertEquals(Genome.NORTH_EAST, Genome.WEST.applyRotation(Genome.SOUTH_EAST));
    }
}
