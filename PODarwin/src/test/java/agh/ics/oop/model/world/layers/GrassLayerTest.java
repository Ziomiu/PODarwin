package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.service.GrassFactory;
import agh.ics.oop.model.world.phases.GrowGrassPhase;
import agh.ics.oop.model.world.phases.InitPhase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrassLayerTest {
    @Test
    void shouldFillMapWithGrassOnInit() {
        Boundary boundary = new Boundary(new Vector2D(0, 0), new Vector2D(9, 9));
        HashSet<Vector2D> blocked = new HashSet<>();
        HashMap<Vector2D, Vector2D> blockedHoles = new HashMap<>();
        blockedHoles.put(new Vector2D(5, 1), new Vector2D(0, 0));
        blockedHoles.put(new Vector2D(0, 0), new Vector2D(5, 1));
        GrowGrassPhase growGrassPhase = new GrowGrassPhase();
        growGrassPhase.setBlockedFields(blocked);
        growGrassPhase.setMapBoundary(boundary);
        GrassLayer grassLayer = new GrassLayer(new GrassFactory(5), 100, 35, true);
        InitPhase initPhase = new InitPhase();
        initPhase.setMapBoundary(boundary);
        grassLayer.setEquatorChance(0.9);
        grassLayer.handle(initPhase);
        assertEquals(60, grassLayer.getGrass().size());
        assertEquals(40, grassLayer.getEquatorGrass().size());
    }

    @Test
    void shouldFillOutsideThenEquator() {
        Boundary boundary = new Boundary(new Vector2D(0, 0), new Vector2D(9, 9));
        HashSet<Vector2D> blocked = new HashSet<>();
        HashMap<Vector2D, Vector2D> blockedHoles = new HashMap<>();
        blockedHoles.put(new Vector2D(5, 1), new Vector2D(0, 0));
        blockedHoles.put(new Vector2D(0, 0), new Vector2D(5, 1));
        GrowGrassPhase growGrassPhase = new GrowGrassPhase();
        growGrassPhase.setBlockedFields(blocked);
        growGrassPhase.setMapBoundary(boundary);
        GrassLayer grassLayer = new GrassLayer(new GrassFactory(5), 10, 35, true);
        InitPhase initPhase = new InitPhase();
        initPhase.setMapBoundary(boundary);
        grassLayer.setEquatorChance(0.1);
        grassLayer.handle(initPhase);
        grassLayer.handle(growGrassPhase);
        grassLayer.handle(growGrassPhase);
        assertEquals(60, grassLayer.getGrass().size());
        grassLayer.handle(growGrassPhase);
        assertEquals(40, grassLayer.getEquatorGrass().size());
    }

    @Test
    void shouldFillEquatorThenOutside() {
        Boundary boundary = new Boundary(new Vector2D(0, 0), new Vector2D(9, 9));
        HashSet<Vector2D> blocked = new HashSet<>();
        HashMap<Vector2D, Vector2D> blockedHoles = new HashMap<>();
        blockedHoles.put(new Vector2D(5, 1), new Vector2D(0, 0));
        blockedHoles.put(new Vector2D(0, 0), new Vector2D(5, 1));
        GrowGrassPhase growGrassPhase = new GrowGrassPhase();
        growGrassPhase.setBlockedFields(blocked);
        growGrassPhase.setMapBoundary(boundary);
        GrassLayer grassLayer = new GrassLayer(new GrassFactory(5), 10, 35, true);
        InitPhase initPhase = new InitPhase();
        initPhase.setMapBoundary(boundary);
        grassLayer.setEquatorChance(0.9);
        grassLayer.handle(initPhase);
        grassLayer.handle(growGrassPhase);
        grassLayer.handle(growGrassPhase);
        assertEquals(40, grassLayer.getEquatorGrass().size());
        grassLayer.handle(growGrassPhase);
        grassLayer.handle(growGrassPhase);
        assertEquals(60, grassLayer.getGrass().size());
    }
}
