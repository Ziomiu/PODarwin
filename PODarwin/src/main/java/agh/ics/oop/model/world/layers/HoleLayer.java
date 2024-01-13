package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.Animal;
import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Hole;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.phases.*;
import agh.ics.oop.utils.HoleIterator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HoleLayer extends AbstractLayer {

    private HashMap<Vector2D, Vector2D> holes;

    public HoleLayer(Boundary boundary, int numOfHoles) {
        if (boundary.numberOfFields() / 2 < numOfHoles) {
            throw new IllegalArgumentException("This many holes cant fit on map");
        }
        HoleIterator holeIterator = new HoleIterator(boundary, numOfHoles);
        HashMap<Vector2D, Vector2D> holeMap = new HashMap<>();
        Iterator<Vector2D> iterator = holeIterator.iterator();
        while (iterator.hasNext()) {
            Vector2D key = iterator.next();
            if (iterator.hasNext()) {
                Vector2D value = iterator.next();
                holeMap.put(key, value);
                holeMap.put(value, key);
            }
        }
        this.holes = holeMap;
    }

    @Override
    public void handle(InitPhase phase) {
        phase.setHoles(this.holes);
    }

    @Override
    public void handle(MovePhase phase) {
        HashMap<Animal, Vector2D> movesMap = phase.getNewAnimalMoves();
        movesMap.replaceAll((key, value) -> holes.getOrDefault(value, value));
        phase.setNewAnimalMoves(movesMap);
    }

    @Override
    public void handle(GrowGrassPhase phase) {
        HashSet<Vector2D> positions = phase.getBlockedFields();
        positions.addAll(holes.entrySet()
            .stream()
            .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
            .collect(Collectors.toSet()));
    }

    @Override
    public void handle(SummaryPhase phase) {
        phase.setTunnels(
            holes
                .entrySet()
                .stream()
                .map(entry -> new Hole(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet())
        );
    }


    public HashMap<Vector2D, Vector2D> getHoles() {
        return holes;
    }

    public void setHoles(HashMap<Vector2D, Vector2D> newHoles) {
        HashMap<Vector2D, Vector2D> newData = new HashMap<>();
        for (var entry : newHoles.entrySet()) {
            newData.put(entry.getKey(), entry.getValue());
            newData.put(entry.getValue(), entry.getKey());
        }

        this.holes = newData;
    }
}
