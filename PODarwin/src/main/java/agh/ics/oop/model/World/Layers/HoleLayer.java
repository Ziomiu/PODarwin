package agh.ics.oop.model.World.Layers;

import agh.ics.oop.model.Classes.Animal;
import agh.ics.oop.model.Classes.Boundry;
import agh.ics.oop.model.Classes.Vector2D;
import agh.ics.oop.model.World.Phases.*;
import agh.ics.oop.utils.HoleIterator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HoleLayer implements MapLayer {

    private HashMap<Vector2D, Vector2D> holes;

    public HoleLayer(Boundry boundry, int numOfHoles) {
        if (boundry.numberOfFields() / 2 < numOfHoles) {
            throw new IllegalArgumentException("This many holes cant fit on map");
        }
        HoleIterator holeIterator = new HoleIterator(boundry, numOfHoles);
        HashMap<Vector2D, Vector2D> holeMap = new HashMap<>();
        Iterator<Vector2D> iterator = holeIterator.iterator();
        while (iterator.hasNext()) {
            Vector2D key = iterator.next();
            if (iterator.hasNext()) {
                Vector2D value = iterator.next();
                holeMap.put(key, value);
            }
        }
        this.holes = holeMap;
    }

    @Override
    public boolean handle(MovePhase phase) {
        HashMap<Animal, Vector2D> movesMap = phase.getNewAnimalMoves();
        movesMap.replaceAll((key, value) -> holes.getOrDefault(value, value));
        phase.setNewAnimalMoves(movesMap);
        return true;
    }

    @Override
    public boolean handle(GrowGrassPhase phase) {
        HashSet<Vector2D> positions = phase.getBlockedFields();
        positions.addAll(holes.entrySet()
                .stream()
                .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet()));
        return true;
    }

    //For now only for testing purposes
    public HashMap<Vector2D, Vector2D> getHoles() {
        return holes;
    }

    public void setHoles(HashMap<Vector2D, Vector2D> newHoles) {
        this.holes = newHoles;
    }
}
