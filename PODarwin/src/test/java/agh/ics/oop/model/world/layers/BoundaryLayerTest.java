package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.model.world.phases.MovePhase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundaryLayerTest {
    private final Vector2D posInside1 = new Vector2D(2, 2);
    private final Vector2D posInside2 = new Vector2D(4, 4);
    private final Vector2D posNorthEdge = new Vector2D(3, 10);
    private final Vector2D posEastEdge = new Vector2D(10, 5);
    private final Vector2D posSouthEdge = new Vector2D(3, 0);
    private final Vector2D posWestEdge = new Vector2D(0, 5);
    private final Vector2D posOOBNorth = new Vector2D(3, 11);
    private final Vector2D posOOBEast = new Vector2D(11, 5);
    private final Vector2D posOOBSouth = new Vector2D(3, -1);
    private final Vector2D posOOBWest = new Vector2D(-1, 5);
    private final Vector2D posNorthEast = new Vector2D(10, 10);
    private final Vector2D posSouthEast = new Vector2D(10, 0);
    private final Vector2D posSouthWest = new Vector2D(0, 0);
    private final Vector2D posNorthWest = new Vector2D(0, 10);
    private final Vector2D posOOBNorthEast = new Vector2D(11, 11);
    private final Vector2D posOOBSouthEast = new Vector2D(11, -1);
    private final Vector2D posOOBSouthWest = new Vector2D(-1, -1);
    private final Vector2D posOOBNorthWest = new Vector2D(-1, 11);

    @Test
    public void shouldPreventFromGoingOutOfBounds() {
        MovePhase movePhase = new MovePhase();
        HashMap<Animal, Vector2D> newMovesMap = new HashMap<>();

        var animal1 = new Animal(posInside1, new OrderedGenomeSequence(List.of(Genome.NORTH_EAST)), 5);
        var animal2 = new Animal(posNorthEdge, new OrderedGenomeSequence(List.of(Genome.NORTH)), 5);
        var animal3 = new Animal(posEastEdge, new OrderedGenomeSequence(List.of(Genome.EAST)), 5);
        var animal4 = new Animal(posSouthEdge, new OrderedGenomeSequence(List.of(Genome.SOUTH)), 5);
        var animal5 = new Animal(posWestEdge, new OrderedGenomeSequence(List.of(Genome.WEST)), 5);
        var animal6 = new Animal(posNorthEast, new OrderedGenomeSequence(List.of(Genome.NORTH_EAST)), 5);
        var animal7 = new Animal(posSouthEast, new OrderedGenomeSequence(List.of(Genome.SOUTH_EAST)), 5);
        var animal8 = new Animal(posSouthWest, new OrderedGenomeSequence(List.of(Genome.SOUTH_WEST)), 5);
        var animal9 = new Animal(posNorthWest, new OrderedGenomeSequence(List.of(Genome.NORTH_WEST)), 5);
        newMovesMap.put(animal1, posInside2);
        newMovesMap.put(animal2, posOOBNorth);
        newMovesMap.put(animal3, posOOBEast);
        newMovesMap.put(animal4, posOOBSouth);
        newMovesMap.put(animal5, posOOBWest);
        newMovesMap.put(animal6, posOOBNorthEast);
        newMovesMap.put(animal7, posOOBSouthEast);
        newMovesMap.put(animal8, posOOBSouthWest);
        newMovesMap.put(animal9, posOOBNorthWest);

        movePhase.setNewAnimalMoves(newMovesMap);
        BoundaryLayer boundaryLayer = new BoundaryLayer(
            new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)),
            false
        );
        boundaryLayer.handle(movePhase);

        assertEquals(posInside2, movePhase.getNewAnimalMoves().get(animal1));
        assertEquals(posNorthEdge, movePhase.getNewAnimalMoves().get(animal2));
        assertEquals(posEastEdge, movePhase.getNewAnimalMoves().get(animal3));
        assertEquals(posSouthEdge, movePhase.getNewAnimalMoves().get(animal4));
        assertEquals(posWestEdge, movePhase.getNewAnimalMoves().get(animal5));
        assertEquals(posNorthEast, movePhase.getNewAnimalMoves().get(animal6));
        assertEquals(posSouthEast, movePhase.getNewAnimalMoves().get(animal7));
        assertEquals(posSouthWest, movePhase.getNewAnimalMoves().get(animal8));
        assertEquals(posNorthWest, movePhase.getNewAnimalMoves().get(animal9));

        assertEquals(Genome.NORTH_EAST, animal1.getCurrentGenome());
        assertEquals(Genome.SOUTH, animal2.getCurrentGenome());
        assertEquals(Genome.WEST, animal3.getCurrentGenome());
        assertEquals(Genome.NORTH, animal4.getCurrentGenome());
        assertEquals(Genome.EAST, animal5.getCurrentGenome());
        assertEquals(Genome.SOUTH_WEST, animal6.getCurrentGenome());
        assertEquals(Genome.NORTH_WEST, animal7.getCurrentGenome());
        assertEquals(Genome.NORTH_EAST, animal8.getCurrentGenome());
        assertEquals(Genome.SOUTH_EAST, animal9.getCurrentGenome());
    }

    @Test
    public void shouldWrapHorizontally() {
        MovePhase movePhase = new MovePhase();
        HashMap<Animal, Vector2D> newMovesMap = new HashMap<>();

        var animal1 = new Animal(posInside1, new OrderedGenomeSequence(List.of(Genome.NORTH_EAST)), 5);
        var animal2 = new Animal(posNorthEdge, new OrderedGenomeSequence(List.of(Genome.NORTH)), 5);
        var animal3 = new Animal(posEastEdge, new OrderedGenomeSequence(List.of(Genome.EAST)), 5);
        var animal4 = new Animal(posSouthEdge, new OrderedGenomeSequence(List.of(Genome.SOUTH)), 5);
        var animal5 = new Animal(posWestEdge, new OrderedGenomeSequence(List.of(Genome.WEST)), 5);
        var animal6 = new Animal(posNorthEast, new OrderedGenomeSequence(List.of(Genome.NORTH_EAST)), 5);
        var animal7 = new Animal(posSouthEast, new OrderedGenomeSequence(List.of(Genome.SOUTH_EAST)), 5);
        var animal8 = new Animal(posSouthWest, new OrderedGenomeSequence(List.of(Genome.SOUTH_WEST)), 5);
        var animal9 = new Animal(posNorthWest, new OrderedGenomeSequence(List.of(Genome.NORTH_WEST)), 5);
        newMovesMap.put(animal1, posInside2);
        newMovesMap.put(animal2, posOOBNorth);
        newMovesMap.put(animal3, posOOBEast);
        newMovesMap.put(animal4, posOOBSouth);
        newMovesMap.put(animal5, posOOBWest);
        newMovesMap.put(animal6, posOOBNorthEast);
        newMovesMap.put(animal7, posOOBSouthEast);
        newMovesMap.put(animal8, posOOBSouthWest);
        newMovesMap.put(animal9, posOOBNorthWest);

        movePhase.setNewAnimalMoves(newMovesMap);
        BoundaryLayer boundaryLayer = new BoundaryLayer(
            new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)),
            true
        );
        boundaryLayer.handle(movePhase);

        assertEquals(posInside2, movePhase.getNewAnimalMoves().get(animal1));
        assertEquals(posNorthEdge, movePhase.getNewAnimalMoves().get(animal2));
        assertEquals(posWestEdge, movePhase.getNewAnimalMoves().get(animal3));
        assertEquals(posSouthEdge, movePhase.getNewAnimalMoves().get(animal4));
        assertEquals(posEastEdge, movePhase.getNewAnimalMoves().get(animal5));
        assertEquals(posNorthEast, movePhase.getNewAnimalMoves().get(animal6));
        assertEquals(posSouthEast, movePhase.getNewAnimalMoves().get(animal7));
        assertEquals(posSouthWest, movePhase.getNewAnimalMoves().get(animal8));
        assertEquals(posNorthWest, movePhase.getNewAnimalMoves().get(animal9));

        assertEquals(Genome.NORTH_EAST, animal1.getCurrentGenome());
        assertEquals(Genome.SOUTH, animal2.getCurrentGenome());
        assertEquals(Genome.EAST, animal3.getCurrentGenome());
        assertEquals(Genome.NORTH, animal4.getCurrentGenome());
        assertEquals(Genome.WEST, animal5.getCurrentGenome());
        assertEquals(Genome.SOUTH_WEST, animal6.getCurrentGenome());
        assertEquals(Genome.NORTH_WEST, animal7.getCurrentGenome());
        assertEquals(Genome.NORTH_EAST, animal8.getCurrentGenome());
        assertEquals(Genome.SOUTH_EAST, animal9.getCurrentGenome());
    }
}
