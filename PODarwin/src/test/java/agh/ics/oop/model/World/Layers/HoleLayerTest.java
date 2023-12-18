package agh.ics.oop.model.World.Layers;

import agh.ics.oop.model.Classes.*;
import agh.ics.oop.model.EnumClasses.Genome;
import agh.ics.oop.model.World.Phases.GrowGrassPhase;
import agh.ics.oop.model.World.Phases.MovePhase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HoleLayerTest {

    @Test
    void shouldChangePositions() {
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(2, 2);
        Vector2D v3 = new Vector2D(5, 5);
        Vector2D v4 = new Vector2D(7, 7);
        HashMap<Vector2D, Vector2D> map = new HashMap<>();
        MovePhase phase = new MovePhase();
        map.put(v1, v3);
        map.put(v2, v4);
        HoleLayer layer = new HoleLayer(new Boundry(new Vector2D(0, 0), new Vector2D(10, 10)), 5);
        layer.setHoles(map);
        HashMap<Animal, Vector2D> animalMap = new HashMap<>();
        ArrayList<Genome> genList = new ArrayList<>();
        genList.add(Genome.NORTH);
        Animal animal1 = new Animal(v1, new AlternatingGenomeSequence(genList), 5);
        Animal animal2 = new Animal(v1, new AlternatingGenomeSequence(genList), 5);
        animalMap.put(animal1, v1);
        animalMap.put(animal2, v2);
        phase.setNewAnimalMoves(animalMap);
        layer.handle(phase);
        animalMap = phase.getNewAnimalMoves();
        assertEquals(v3, animalMap.get(animal1));
        assertEquals(v4, animalMap.get(animal2));
    }

    @Test
    void shouldReturnAllHoles() {
        GrowGrassPhase phase = new GrowGrassPhase();
        phase.setBlockedFields(new HashSet<>());
        HoleLayer layer = new HoleLayer(new Boundry(new Vector2D(0, 0), new Vector2D(5, 6)), 5);
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
        HoleLayer layer = new HoleLayer(new Boundry(new Vector2D(0, 0), new Vector2D(10, 10)), 5);
        assertEquals(5, layer.getHoles().size());
    }
}