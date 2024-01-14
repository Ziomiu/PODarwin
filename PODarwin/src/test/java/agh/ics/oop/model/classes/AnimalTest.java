package agh.ics.oop.model.classes;

import agh.ics.oop.model.enums.Genome;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AnimalTest {
    @Test
    public void shouldCreateAnimal() {
        Animal animal = new Animal(
            new Vector2D(2, 2),
            new OrderedGenomeSequence(List.of(Genome.NORTH, Genome.EAST, Genome.SOUTH_WEST)),
            10
        );

        assertEquals(new Vector2D(2, 2), animal.getPosition());
        assertEquals(Genome.NORTH, animal.getCurrentGenome());
        assertEquals(10, animal.getEnergy());
    }

    @Test
    void shouldCreateAnimalsWithCorrectDescendantsCount() {
        Animal a1 = new Animal(mock(Vector2D.class), mock(GenomeSequence.class), 10);
        Animal a2 = new Animal(mock(Vector2D.class), mock(GenomeSequence.class), 10);
        Animal a3 = new Animal(mock(Vector2D.class), mock(GenomeSequence.class), a1, a2, 10);
        Animal a4 = new Animal(mock(Vector2D.class), mock(GenomeSequence.class), a1, a2, 10);
        Animal a5 = new Animal(mock(Vector2D.class), mock(GenomeSequence.class), a3, a4, 10);
        assertEquals(2, a1.getAnimalStats().getNumOfChildren());
        assertEquals(2, a2.getAnimalStats().getNumOfChildren());
        assertEquals(1, a3.getAnimalStats().getNumOfChildren());
        assertEquals(1, a4.getAnimalStats().getNumOfChildren());
        assertEquals(0, a5.getAnimalStats().getNumOfChildren());

        assertEquals(3, a1.getAnimalStats().getNumOfDescendants());
        assertEquals(3, a2.getAnimalStats().getNumOfDescendants());
        assertEquals(1, a3.getAnimalStats().getNumOfDescendants());
        assertEquals(1, a4.getAnimalStats().getNumOfDescendants());
        assertEquals(0, a5.getAnimalStats().getNumOfDescendants());
    }

    @Test
    public void shouldDisallowCreatingAnimalWithZeroEnergy() {
        assertThrows(
            IllegalStateException.class,
            () -> new Animal(new Vector2D(2, 2), mock(GenomeSequence.class), 0)
        );
    }

    @Test
    public void shouldSetPosition() {
        Animal animal = new Animal(
            new Vector2D(2, 2),
            mock(GenomeSequence.class),
            10
        );
        Vector2D vec = new Vector2D(10, 10);
        animal.setPosition(vec);
        assertEquals(vec, animal.getPosition());
    }

    @Test
    public void shouldChangeEnergyState() {
        Animal animal = new Animal(
            new Vector2D(2, 2),
            mock(GenomeSequence.class),
            10
        );
        animal.removeEnergy(5);
        animal.addEnergy(2);
        assertEquals(7, animal.getEnergy());

        assertThrows(IllegalArgumentException.class, () -> animal.addEnergy(-1));
        assertThrows(IllegalArgumentException.class, () -> animal.removeEnergy(-1));
    }

    @Test
    public void shouldGetNextGenome() {
        var sequenceMock = mock(GenomeSequence.class);
        when(sequenceMock.nextInSequence()).thenReturn(Genome.NORTH).thenReturn(Genome.WEST);
        Animal animal = new Animal(new Vector2D(2, 2), sequenceMock, 10);
        animal.nextGenome();
        assertEquals(Genome.WEST, animal.getCurrentGenome());
        verify(sequenceMock, times(2)).nextInSequence();
    }

    @Test
    public void shouldGetEffectiveGenome() {
        var sequenceMock = mock(GenomeSequence.class);
        when(sequenceMock.nextInSequence()).thenReturn(Genome.NORTH).thenReturn(Genome.WEST).thenReturn(Genome.SOUTH);
        Animal animal = new Animal(new Vector2D(2, 2), sequenceMock, 10);
        animal.nextGenome();
        animal.nextGenome();
        assertEquals(Genome.EAST, animal.getEffectiveGenome());
        verify(sequenceMock, times(3)).nextInSequence();
    }
}
