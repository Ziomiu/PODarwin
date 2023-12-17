package agh.ics.oop.model.classes;

import agh.ics.oop.model.Classes.AlternatingGenomeSequence;
import agh.ics.oop.model.EnumClasses.Genome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlternatingGenomeSequenceTest {
    private final List<Genome> genomes;
    private AlternatingGenomeSequence sequence;

    {
        genomes = List.of(Genome.NORTH, Genome.NORTH_EAST, Genome.EAST);
    }

    @BeforeEach
    public void setUp() {
        sequence = new AlternatingGenomeSequence(genomes);
    }

    @Test
    public void shouldAlternate() {
        var generatedValues = IntStream
            .range(0, 9)
            .mapToObj((i) -> sequence.nextInSequence())
            .toList();

        assertEquals(
            List.of(
                Genome.NORTH, Genome.NORTH_EAST, Genome.EAST,
                Genome.EAST, Genome.NORTH_EAST, Genome.NORTH,
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
