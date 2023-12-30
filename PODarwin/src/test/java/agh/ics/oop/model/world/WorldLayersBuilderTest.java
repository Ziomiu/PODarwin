package agh.ics.oop.model.world;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.ReproductionParams;
import agh.ics.oop.model.classes.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WorldLayersBuilderTest {
    @Test
    public void shouldThrowOnInvalidTunnelsCount() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.withTunnels(-1));
    }

    @Test
    public void shouldThrowOnInvalidGrassGrownEachIteration() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.withGrassGrownEachIteration(-1));
    }

    @Test
    public void shouldThrowOnInvalidGrassCount() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.withInitialGrassCount(-1));
    }

    @Test
    public void shouldThrowOnInvalidInitialAnimalEnergy() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.withInitialAnimalsEnergy(-1));
        assertThrows(IllegalArgumentException.class, () -> builder.withInitialAnimalsEnergy(0));
    }

    @Test
    public void shouldThrowOnInvalidEnergyOfGrass() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.withEnergyOfGrass(-1));
    }

    @Test
    public void shouldThrowOnInvalidLengthOfGenome() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.withGenomeLength(-1));
        assertThrows(IllegalArgumentException.class, () -> builder.withGenomeLength(0));
    }

    @Test
    public void shouldThrowOnInvalidReproductionParams() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.withReproductionParams(
            new ReproductionParams(-1, 10, 10, 20)
        ));
        assertThrows(IllegalArgumentException.class, () -> builder.withReproductionParams(
            new ReproductionParams(10, -1, 10, 20)
        ));
        assertThrows(IllegalArgumentException.class, () -> builder.withReproductionParams(
            new ReproductionParams(10, 10, -1, 20)
        ));
        assertThrows(IllegalArgumentException.class, () -> builder.withReproductionParams(
            new ReproductionParams(10, 10, 10, -1)
        ));
        assertThrows(IllegalArgumentException.class, () -> builder.withReproductionParams(
            new ReproductionParams(10, 10, 20, 10)
        ));
    }

    @Test
    public void shouldThrowOnMissingReproductionParams() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        builder
            .withBoundary(new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)))
            .withInitialAnimalsEnergy(10);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    public void shouldThrowOnMissingBoundary() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        builder
            .withReproductionParams(new ReproductionParams(10, 10, 10, 20))
            .withInitialAnimalsEnergy(10);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    public void shouldThrowOnMissingInitialAnimalEnergy() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        builder
            .withReproductionParams(new ReproductionParams(10, 10, 10, 20))
            .withBoundary(new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)));

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    public void shouldThrowOnTooMuchObjects() {
        WorldLayersBuilder builder = new WorldLayersBuilder();
        builder
            .withReproductionParams(new ReproductionParams(10, 10, 10, 20))
            .withBoundary(new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)))
            .withInitialAnimalsEnergy(10)
            .withInitialGrassCount(90)
            .withTunnels(32);

        assertThrows(IllegalStateException.class, builder::build);
    }
}
