package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.classes.factory.GrassFactory;
import agh.ics.oop.model.world.phases.CleanupPhase;
import agh.ics.oop.model.world.phases.EatPhase;
import agh.ics.oop.model.world.phases.GrowGrassPhase;
import agh.ics.oop.model.world.phases.InitPhase;
import agh.ics.oop.utils.PositionsRange;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GrassLayer extends AbstractLayer {
    private final GrassFactory grassFactory;
    private final int initialGrassPatchesCount;
    private final int grassGrownEachPhase;

    private final boolean hasEquator;
    private HashSet<Grass> grass = new HashSet<>();
    private HashSet<Grass> equatorGrass = new HashSet<>();
    private Boundary equator = null;
    private double equatorChance = 0.8;

    public GrassLayer(
        GrassFactory grassFactory,
        int initialGrassPatchesCount,
        int grassGrownEachPhase,
        boolean hasEquator
    ) {
        this.grassFactory = grassFactory;
        this.initialGrassPatchesCount = initialGrassPatchesCount;
        this.grassGrownEachPhase = grassGrownEachPhase;
        this.hasEquator = hasEquator;
    }

    private void createEquator(Boundary boundary) {
        if (boundary.height() % 2 == 0) {
            this.equator = new Boundary(new Vector2D(0, (boundary.height() / 2) - 1 - boundary.height() / 10),
                new Vector2D(boundary.upper().x(), boundary.height() / 2 + boundary.height() / 10));
        } else {
            this.equator = new Boundary(new Vector2D(0, boundary.height() / 2 - boundary.height() / 10),
                new Vector2D(boundary.upper().x(), boundary.height() / 2 + boundary.height() / 10 + 1));
        }
    }

    private HashSet<Vector2D> getAllGrassPositions() {
        var grasses = new ArrayList<>(this.grass);
        grasses.addAll(this.equatorGrass);
        List<Vector2D> allGrasses = grasses.stream()
            .map(Grass::getPosition)
            .toList();
        return new HashSet<>(allGrasses);
    }

    @Override
    public void handle(InitPhase phase) {
        if (!this.hasEquator) {
            phase.setGrasses(this.grass);
            growGrass(phase.getMapBoundary(), this.initialGrassPatchesCount,
                0, new HashSet<>(phase.getHoles().keySet()), new HashSet<>(phase.getHoles().keySet()));
            phase.setGrasses(this.grass);
            return;
        }

        createEquator(phase.getMapBoundary());
        int freeEquatorFields = this.equator.numberOfFields();
        int freeNotEquatorFields = phase.getMapBoundary().numberOfFields() - this.equator.numberOfFields();
        var occupied = new HashSet<>(phase.getHoles().keySet());
        occupied.addAll(this.equator.generateAllPositions());
        findPlacesToGrowGrass(phase.getMapBoundary(), freeNotEquatorFields, freeEquatorFields, occupied, new HashSet<>(phase.getHoles().keySet()), initialGrassPatchesCount);
        var allGrasses = new HashSet<>(this.grass);
        allGrasses.addAll(this.equatorGrass);
        phase.setGrasses(allGrasses);
    }

    @Override
    public void handle(EatPhase phase) {
        var allGrasses = new HashSet<>(this.grass);
        allGrasses.addAll(this.equatorGrass);
        phase.setGrass(allGrasses);
    }

    private void growGrass(Boundary boundary, int grassOutsideOfEquator, int grassOnEquator,
                           HashSet<Vector2D> occupiedOutsideOfEquator, HashSet<Vector2D> occupiedOnEquator) {
        PositionsRange positionsRange = new PositionsRange(boundary, grassOutsideOfEquator, occupiedOutsideOfEquator);
        for (Vector2D position : positionsRange) {
            this.grass.add(grassFactory.getGrassPatch(position));
        }
        if (this.hasEquator) {
            PositionsRange positionsRangeEquator = new PositionsRange(this.equator, grassOnEquator, occupiedOnEquator);
            for (Vector2D position : positionsRangeEquator) {
                this.equatorGrass.add(grassFactory.getGrassPatch(position));
            }
        }
    }

    private void findPlacesToGrowGrass(Boundary boundary, int grassOutsideOfEquator, int grassOnEquator,
                                       HashSet<Vector2D> occupiedOutsideOfEquator, HashSet<Vector2D> occupiedOnEquator, int amount) {
        if (grassOutsideOfEquator >= (int) Math.floor(amount * (1 - this.equatorChance))) {
            //When there is enough place on the equator and outside of it
            if (grassOnEquator >= (int) Math.floor(amount * this.equatorChance)) {
                growGrass(boundary, (int) Math.floor(amount * (1 - this.equatorChance)),
                    (int) Math.floor(amount * this.equatorChance), occupiedOutsideOfEquator, occupiedOnEquator);
                //When there isn't enough place on the equator, but it is outside of it for grass grown each phase
            } else {
                if (grassOutsideOfEquator >= amount + (amount - grassOnEquator)) {
                    growGrass(boundary, amount + (amount - grassOnEquator),
                        grassOnEquator, occupiedOutsideOfEquator, occupiedOnEquator);
                } else {
                    growGrass(boundary, grassOutsideOfEquator,
                        grassOnEquator, occupiedOutsideOfEquator, occupiedOnEquator);
                }
            }
            //When there is enough place on the equator for grass grown each phase, but it isn't outside of it
        } else {
            if (grassOnEquator >= (int) Math.floor(amount * this.equatorChance)) {
                if (grassOnEquator >= amount + (amount - grassOnEquator)) {
                    growGrass(boundary, grassOnEquator, amount + (amount - grassOnEquator),
                        occupiedOutsideOfEquator, occupiedOnEquator);
                } else {
                    growGrass(boundary, grassOutsideOfEquator, grassOnEquator,
                        occupiedOutsideOfEquator, occupiedOnEquator);
                }
                //When there isn't enough place for grass grown each phase on the equator and outside of it
            } else {
                growGrass(boundary, grassOutsideOfEquator, grassOnEquator,
                    occupiedOutsideOfEquator, occupiedOnEquator);
            }
        }
    }

    @Override
    public void handle(GrowGrassPhase phase) {
        // todo: try to find better place for it
        phase.getEatenGrass().forEach(this.grass::remove);
        phase.getEatenGrass().forEach(this.equatorGrass::remove);

        var blockedFields = phase.getBlockedFields();
        blockedFields.addAll(getAllGrassPositions());
        phase.setBlockedFields(blockedFields);
        if (this.hasEquator) {
            int freeEquatorFields = this.equator.numberOfFields() - this.equatorGrass.size();
            int freeNotEquatorFields = phase.getMapBoundary().numberOfFields() - this.grass.size() - this.equator.numberOfFields();
            var occupied = new HashSet<>(phase.getBlockedFields());
            occupied.addAll(this.equator.generateAllPositions());
            findPlacesToGrowGrass(phase.getMapBoundary(), freeNotEquatorFields, freeEquatorFields, occupied, phase.getBlockedFields(), grassGrownEachPhase);
            return;
        }

        if (phase.getMapBoundary().numberOfFields() - phase.getBlockedFields().size() >= this.grassGrownEachPhase) {
            growGrass(phase.getMapBoundary(), this.grassGrownEachPhase, 0, phase.getBlockedFields(), phase.getBlockedFields());
            return;
        }

        growGrass(phase.getMapBoundary(), phase.getMapBoundary().numberOfFields() - phase.getBlockedFields().size(),
            0, phase.getBlockedFields(), phase.getBlockedFields());
    }

    public HashSet<Grass> getGrass() {
        return grass;
    }

    public HashSet<Grass> getEquatorGrass() {
        return equatorGrass;
    }

    public Boundary getEquator() {
        return equator;
    }

    public void setEquatorChance(double equatorChance) {
        this.equatorChance = equatorChance;
    }
}
