package agh.ics.oop.model.classes;

import agh.ics.oop.model.enums.Genome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderedGenomeSequenceTest {
    private final List<Genome> genomes;
    private OrderedGenomeSequence sequence;

    {
        genomes = List.of(Genome.NORTH, Genome.NORTH_EAST, Genome.EAST);
    }

    @BeforeEach
    public void setUp() {
        sequence = new OrderedGenomeSequence(genomes);
    }

    @Test
    public void shouldLoop() {
        var generatedValues = IntStream
            .range(0, 9)
            .mapToObj((i) -> sequence.nextInSequence())
            .toList();

        assertEquals(
            List.of(
                Genome.NORTH, Genome.NORTH_EAST, Genome.EAST,
                Genome.NORTH, Genome.NORTH_EAST, Genome.EAST,
                Genome.NORTH, Genome.NORTH_EAST, Genome.EAST
            ),
            generatedValues
        );
    }

    @Test
    public void shouldGetAllGenomes() {
        assertEquals(genomes, sequence.getAllGenomes());
    }
}
