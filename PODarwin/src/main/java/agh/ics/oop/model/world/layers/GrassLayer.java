package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.factory.GrassFactory;

public class GrassLayer implements MapLayer {
    private final GrassFactory grassFactory;
    private final int grassGrownEachPhase;
    private final boolean hasEquator;

    public GrassLayer(
        GrassFactory grassFactory,
        int initialGrassPatchesCount,
        int grassGrownEachPhase,
        boolean hasEquator
    ) {
        this.grassFactory = grassFactory;
        this.grassGrownEachPhase = grassGrownEachPhase;
        this.hasEquator = hasEquator;
    }
}
