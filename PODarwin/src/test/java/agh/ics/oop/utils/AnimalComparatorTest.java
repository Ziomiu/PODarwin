package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Animal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AnimalComparatorTest {
    @Test
    void shouldCompareAnimals() {
        var a1 = mock(Animal.class, RETURNS_DEEP_STUBS);
        when(a1.getEnergy()).thenReturn(10);
        when(a1.getAnimalStats().getAge()).thenReturn(10);
        when(a1.getAnimalStats().getNumOfChildren()).thenReturn(0);

        var a2 = mock(Animal.class, RETURNS_DEEP_STUBS);
        when(a2.getEnergy()).thenReturn(20);
        when(a2.getAnimalStats().getAge()).thenReturn(10);
        when(a2.getAnimalStats().getNumOfChildren()).thenReturn(0);

        var a3 = mock(Animal.class, RETURNS_DEEP_STUBS);
        when(a3.getEnergy()).thenReturn(10);
        when(a3.getAnimalStats().getAge()).thenReturn(12);
        when(a3.getAnimalStats().getNumOfChildren()).thenReturn(0);

        var a4 = mock(Animal.class, RETURNS_DEEP_STUBS);
        when(a4.getEnergy()).thenReturn(10);
        when(a4.getAnimalStats().getAge()).thenReturn(10);
        when(a4.getAnimalStats().getNumOfChildren()).thenReturn(3);

        var a5 = mock(Animal.class, RETURNS_DEEP_STUBS);
        when(a5.getEnergy()).thenReturn(10);
        when(a5.getAnimalStats().getAge()).thenReturn(5);
        when(a5.getAnimalStats().getNumOfChildren()).thenReturn(3);

        AnimalComparator comparator = new AnimalComparator();

        assertTrue(comparator.compare(a2, a1) > 0);
        assertTrue(comparator.compare(a1, a2) < 0);

        assertTrue(comparator.compare(a3, a1) > 0);
        assertTrue(comparator.compare(a1, a3) < 0);

        assertTrue(comparator.compare(a4, a1) > 0);
        assertTrue(comparator.compare(a1, a4) < 0);

        assertTrue(comparator.compare(a1, a5) > 0);
        assertTrue(comparator.compare(a5, a1) < 0);
    }
}
