package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.world.phases.GrowGrassPhase;
import agh.ics.oop.model.world.phases.MovePhase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HoleLayerTest {

    @Test
    void shouldChangePositions() {
        Vector2D v0 = new Vector2D(0, 0);
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(2, 2);
        Vector2D v3 = new Vector2D(5, 5);
        Vector2D v4 = new Vector2D(7, 7);
        Vector2D v5 = new Vector2D(2, 3);
        HashMap<Vector2D, Vector2D> map = new HashMap<>();
        MovePhase phase = new MovePhase();
        map.put(v1, v3);
        map.put(v2, v4);
        HoleLayer layer = new HoleLayer(new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)), 5);
        layer.setHoles(map);
        HashMap<Animal, Vector2D> animalMap = new HashMap<>();
        Animal animal1 = new Animal(v0, mock(GenomeSequence.class), 5);
        Animal animal2 = new Animal(v0, mock(GenomeSequence.class), 5);
        Animal animal3 = new Animal(v0, mock(GenomeSequence.class), 5);
        Animal animal4 = new Animal(v0, mock(GenomeSequence.class), 5);
        animalMap.put(animal1, v1);
        animalMap.put(animal2, v2);
        animalMap.put(animal3, v3);
        animalMap.put(animal4, v5);
        phase.setNewAnimalMoves(animalMap);
        layer.handle(phase);
        animalMap = phase.getNewAnimalMoves();
        assertEquals(v3, animalMap.get(animal1));
        assertEquals(v4, animalMap.get(animal2));
        assertEquals(v1, animalMap.get(animal3));
        assertEquals(v5, animalMap.get(animal4));
    }

    @Test
    void shouldReturnAllHoles() {
        GrowGrassPhase phase = new GrowGrassPhase();
        phase.setBlockedFields(new HashSet<>());
        HoleLayer layer = new HoleLayer(new Boundary(new Vector2D(0, 0), new Vector2D(5, 6)), 5);
        layer.handle(phase);
        HashMap<Vector2D, Vector2D> map = layer.getHoles();
        HashSet<Vector2D> positions = phase.getBlockedFields();
        map.forEach((key, value) -> {
            Assertions.assertTrue(positions.contains(key));
            Assertions.assertTrue(positions.contains(value));
        });
    }

    @Test
    void shouldGenerateNHoles() {
        HoleLayer layer = new HoleLayer(new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)), 5);
        assertEquals(10, layer.getHoles().size());
    }
}
