package agh.ics.oop;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.ReproductionParams;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.WorldLayersBuilder;
import agh.ics.oop.model.world.layers.MapLayer;

public class World {
    public static void main(String[] args) {
        System.out.println("Hello Darwin World!");

        WorldLayersBuilder builder = new WorldLayersBuilder();
        builder
            .withAlternatingGenomes()
            .withEquator()
            .withWrappingWorld()
            .withBoundary(new Boundary(new Vector2D(0, 0), new Vector2D(100, 100)))
            .withTunnels(10)
            .withGenomeLength(20)
            .withInitialGrassCount(1000)
            .withGrassGrownEachIteration(10)
            .withEnergyOfGrass(10)
            .withInitialAnimalsEnergy(100)
            .withReproductionParams(new ReproductionParams(
                50,
                30,
                1,
                5
            ));

        MapLayer firstLayer = builder.build();
    }
}
