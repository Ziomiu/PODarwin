package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.classes.factory.GrassFactory;
import agh.ics.oop.model.world.phases.CleanupPhase;
import agh.ics.oop.model.world.phases.EatPhase;
import agh.ics.oop.model.world.phases.GrowGrassPhase;
import agh.ics.oop.model.world.phases.InitPhase;
import agh.ics.oop.utils.GrassIterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class GrassLayer implements MapLayer {
    private final GrassFactory grassFactory;
    private final int initialGrassPatchesCount;
    private final int grassGrownEachPhase;

    private final boolean hasEquator;
    private HashSet<Grass> grasses = new HashSet<>();
    private HashSet<Grass> equatorGrasses = new HashSet<>();
    private Boundary equator = null;
    private double equatorChance = 0.1;

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

    private void CreateEquator(Boundary boundary) {
        if (boundary.height() % 2 == 0) {
            this.equator = new Boundary(new Vector2D(0, boundary.height() / 2 - boundary.height() / 10),
                new Vector2D(boundary.upper().x(), boundary.height() / 2 + boundary.height() / 10));
        } else {
            this.equator = new Boundary(new Vector2D(0, boundary.height() / 2 - boundary.height() / 10),
                new Vector2D(boundary.upper().x(), boundary.height() / 2 + boundary.height() / 10 + 1));
        }
    }

    private HashSet<Vector2D> getAllGrassesPositions() {
        var grasses = new ArrayList<>(this.grasses);
        grasses.addAll(this.equatorGrasses);
        List<Vector2D> allGrasses = grasses.stream()
            .map(Grass::getPosition)
            .toList();
        return new HashSet<>(allGrasses);
    }

    @Override
    public boolean handle(InitPhase phase) {
        if (!this.hasEquator) {
            GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), this.initialGrassPatchesCount, new HashSet<>());
            for (Vector2D position : grassIterator) {
                this.grasses.add(grassFactory.getGrassPatch(position));
            }
            phase.setGrasses(this.grasses);
            return true;
        } else {
            CreateEquator(phase.getMapBoundary());
            HashSet<Vector2D> occupied = new HashSet<>(phase.getHoles().keySet());
            occupied.addAll(this.equator.generateAllPositions());
            GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), (int) Math.ceil(this.initialGrassPatchesCount * (1 - this.equatorChance)), new HashSet<>(occupied));
            for (Vector2D position : grassIterator) {
                this.grasses.add(grassFactory.getGrassPatch(position));
            }
            GrassIterator grassEquatorIterator = new GrassIterator(this.equator, (int) Math.floor(this.initialGrassPatchesCount * this.equatorChance), new HashSet<>(phase.getHoles().keySet()));
            for (Vector2D position : grassEquatorIterator) {
                this.equatorGrasses.add(grassFactory.getGrassPatch(position));
            }
            var allGrasses = new HashSet<>(this.grasses);
            allGrasses.addAll(this.equatorGrasses);
            phase.setGrasses(allGrasses);
            return true;
        }
    }

    @Override
    public boolean handle(EatPhase phase) {
        var allGrasses = new HashSet<>(this.grasses);
        allGrasses.addAll(this.equatorGrasses);
        phase.setGrassPositions(allGrasses);
        return true;
    }

    @Override
    public boolean handle(CleanupPhase phase) {
        phase.getEatenGrass().forEach(this.grasses::remove);
        phase.getEatenGrass().forEach(this.equatorGrasses::remove);
        return true;
    }

    @Override
    public boolean handle(GrowGrassPhase phase) {
        var allGrasses = phase.getBlockedFields();
        allGrasses.addAll(getAllGrassesPositions());
        phase.setBlockedFields(allGrasses);
        if (this.hasEquator) {
            int freeEquatorFields = this.equator.numberOfFields() - this.equatorGrasses.size();
            int freeNotEquatorFields = phase.getMapBoundary().numberOfFields() - this.grasses.size() - this.equator.numberOfFields();
            var occupied = new HashSet<>(phase.getBlockedFields());
            occupied.addAll(this.equator.generateAllPositions());
            if (freeNotEquatorFields >= (int) Math.floor(this.grassGrownEachPhase * (1 - this.equatorChance))) {
                //When there is enough place on the equator and outside of it
                if (freeEquatorFields >= (int) Math.floor(this.grassGrownEachPhase * this.equatorChance)) {
                    GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), (int) Math.ceil(this.grassGrownEachPhase * (1 - this.equatorChance)), new HashSet<>(occupied));
                    for (Vector2D position : grassIterator) {
                        this.grasses.add(grassFactory.getGrassPatch(position));
                    }
                    GrassIterator grassEquatorIterator = new GrassIterator(this.equator, (int) Math.floor(this.grassGrownEachPhase * this.equatorChance), phase.getBlockedFields());
                    for (Vector2D position : grassEquatorIterator) {
                        this.equatorGrasses.add(grassFactory.getGrassPatch(position));
                    }
                    //When there isn't enough place on the equator, but it is outside of it
                } else {
                    if (freeNotEquatorFields >= this.grassGrownEachPhase + (this.grassGrownEachPhase - freeEquatorFields)) {
                        GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), (this.grassGrownEachPhase - freeEquatorFields), phase.getBlockedFields());
                        for (Vector2D position : grassIterator) {
                            this.grasses.add(grassFactory.getGrassPatch(position));
                        }
                    } else {
                        GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), freeNotEquatorFields, phase.getBlockedFields());
                        for (Vector2D position : grassIterator) {
                            this.grasses.add(grassFactory.getGrassPatch(position));
                        }
                    }

                    GrassIterator grassEquatorIterator = new GrassIterator(this.equator, freeEquatorFields, phase.getBlockedFields());
                    for (Vector2D position : grassEquatorIterator) {
                        this.equatorGrasses.add(grassFactory.getGrassPatch(position));
                    }

                }
                //When there is enough place on the equator, but it isnt outside of it
            } else {
                if (freeEquatorFields >= (int) Math.floor(this.grassGrownEachPhase * this.equatorChance)) {
                    GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), freeNotEquatorFields, new HashSet<>(occupied));
                    for (Vector2D position : grassIterator) {
                        this.grasses.add(grassFactory.getGrassPatch(position));
                    }
                    if (freeEquatorFields >= this.grassGrownEachPhase + (this.grassGrownEachPhase - freeNotEquatorFields)) {
                        GrassIterator grassEquatorIterator = new GrassIterator(this.equator, this.grassGrownEachPhase + (this.grassGrownEachPhase - freeNotEquatorFields), phase.getBlockedFields());
                        for (Vector2D position : grassEquatorIterator) {
                            this.equatorGrasses.add(grassFactory.getGrassPatch(position));
                        }
                    } else {
                        GrassIterator grassEquatorIterator = new GrassIterator(this.equator, freeEquatorFields, phase.getBlockedFields());
                        for (Vector2D position : grassEquatorIterator) {
                            this.equatorGrasses.add(grassFactory.getGrassPatch(position));
                        }
                    }
                    //When there isn't enough place on the equator and outside of it
                } else {
                    GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), freeNotEquatorFields, new HashSet<>(occupied));
                    for (Vector2D position : grassIterator) {
                        this.grasses.add(grassFactory.getGrassPatch(position));
                    }
                    GrassIterator grassEquatorIterator = new GrassIterator(this.equator, freeEquatorFields, phase.getBlockedFields());
                    for (Vector2D position : grassEquatorIterator) {
                        this.equatorGrasses.add(grassFactory.getGrassPatch(position));
                    }
                }

            }
        } else {
            if (phase.getMapBoundary().numberOfFields() - phase.getBlockedFields().size() >= this.grassGrownEachPhase) {
                GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), this.grassGrownEachPhase, phase.getBlockedFields());
                for (Vector2D position : grassIterator) {
                    this.grasses.add(grassFactory.getGrassPatch(position));
                }
            } else {
                GrassIterator grassIterator = new GrassIterator(phase.getMapBoundary(), phase.getMapBoundary().numberOfFields() - phase.getBlockedFields().size(), phase.getBlockedFields());
                for (Vector2D position : grassIterator) {
                    this.grasses.add(grassFactory.getGrassPatch(position));
                }
            }

        }
        return true;
    }

    public HashSet<Grass> getGrasses() {
        return grasses;
    }

    public HashSet<Grass> getEquatorGrasses() {
        return equatorGrasses;
    }

    public Boundary getEquator() {
        return equator;
    }
}
