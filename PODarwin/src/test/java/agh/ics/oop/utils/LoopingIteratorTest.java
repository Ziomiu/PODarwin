package agh.ics.oop.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoopingIteratorTest {
    @Test
    public void shouldLoopThroughGivenList() {
        List<Integer> elements = List.of(1, 2, 3);
        LoopingIterator<Integer> iter = new LoopingIterator<>(elements);

        var generatedValues = IntStream
            .range(0, 9)
            .mapToObj((i) -> iter.next())
            .toList();

        assertEquals(List.of(1, 2, 3, 1, 2, 3, 1, 2, 3), generatedValues);
    }

    @Test
    public void shouldStartWithOffset() {
        List<Integer> elements = List.of(1, 2, 3);
        LoopingIterator<Integer> iter = new LoopingIterator<>(elements, 2);

        var generatedValues = IntStream
            .range(0, 9)
            .mapToObj((i) -> iter.next())
            .toList();

        assertEquals(List.of(3, 1, 2, 3, 1, 2, 3, 1, 2), generatedValues);
    }

    @Test
    public void shouldThrowOnEmptyList() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new LoopingIterator<>(new ArrayList<Integer>(0))
        );
    }
}
