package agh.ics.oop.model.classes;

import agh.ics.oop.model.Classes.Animal;
import agh.ics.oop.model.Classes.GenomeSequence;
import agh.ics.oop.model.Classes.OrderedGenomeSequence;
import agh.ics.oop.model.Classes.Vector2D;
import agh.ics.oop.model.EnumClasses.Genome;
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
}
