package agh.ics.oop.model.world;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.classes.factory.AnimalFactory;
import agh.ics.oop.model.classes.factory.GenomeSequenceFactory;
import agh.ics.oop.model.classes.factory.GrassFactory;
import agh.ics.oop.model.world.layers.*;

public class WorldLayersBuilder {
    private boolean hasAlternatingGenomes = false;
    private boolean hasWrappingWorld = false;
    private boolean hasEquator = false;
    private Boundary boundary = null;
    private int initialHoleCount = 0;
    private int initialGrassCount = 0;
    private int grassGrownEachIteration = 0;
    private int initialAnimalEnergy = 0;
    private int energyOfGrass = 0;
    private int genomeLength = 10;
    private ReproductionParams reproductionParams = null;

    public WorldLayersBuilder withBoundary(Boundary boundary) {
        this.boundary = boundary;
        return this;
    }

    public WorldLayersBuilder withAlternatingGenomes() {
        this.hasAlternatingGenomes = true;
        return this;
    }

    public WorldLayersBuilder withWrappingWorld() {
        this.hasWrappingWorld = true;
        return this;
    }

    public WorldLayersBuilder withTunnels(int tunnelsCount) {
        if (tunnelsCount < 0) {
            throw new IllegalArgumentException("Tunnels count must be equal or greater than 0");
        }
        this.initialHoleCount = tunnelsCount;
        return this;
    }

    public WorldLayersBuilder withEquator() {
        this.hasEquator = true;
        return this;
    }

    public WorldLayersBuilder withInitialGrassCount(int initialGrassCount) {
        if (initialGrassCount < 0) {
            throw new IllegalArgumentException("Grass count must be equal or greater than 0");
        }
        this.initialGrassCount = initialGrassCount;
        return this;
    }

    public WorldLayersBuilder withGrassGrownEachIteration(int numOfPatches) {
        if (numOfPatches < 0) {
            throw new IllegalArgumentException("Grass grow must be positive");
        }
        this.grassGrownEachIteration = numOfPatches;
        return this;
    }

    public WorldLayersBuilder withInitialAnimalsEnergy(int energy) {
        if (energy <= 0) {
            throw new IllegalArgumentException("Animals must have > 0 energy");
        }
        this.initialAnimalEnergy = energy;
        return this;
    }

    public WorldLayersBuilder withEnergyOfGrass(int energy) {
        if (energy <= 0) {
            throw new IllegalArgumentException("Eating grass must give > 0 energy");
        }
        this.energyOfGrass = energy;
        return this;
    }

    public WorldLayersBuilder withGenomeLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("There must be at least 1 genome");
        }
        this.genomeLength = length;
        return this;
    }

    public WorldLayersBuilder withReproductionParams(ReproductionParams params) {
        if (!params.validate()) {
            throw new IllegalArgumentException("Invalid reproduction parameters");
        }
        this.reproductionParams = params;
        return this;
    }

    public MapLayer build() throws IllegalStateException {
        if (reproductionParams == null) {
            throw new IllegalStateException("Reproduction parameters are required");
        }

        if (boundary == null) {
            throw new IllegalStateException("Boundary is required");
        }

        if (initialAnimalEnergy <= 0) {
            throw new IllegalStateException("Initial animal energy is required");
        }

        if (initialHoleCount + initialGrassCount > boundary.numberOfFields()) {
            throw new IllegalStateException("Not enough fields to fit objects");
        }
        if (boundary.height() < 2 && hasEquator) {
            throw new IllegalStateException("Equator requires minimal height of 2");
        }

        GenomeSequenceFactory genomeSequenceFactory = new GenomeSequenceFactory();
        AnimalLayer animalLayer = new AnimalLayer(
            new AnimalFactory(initialAnimalEnergy),
            reproductionParams,
            () -> this.hasAlternatingGenomes
                ? genomeSequenceFactory.getRandomAlternatingGenome(genomeLength)
                : genomeSequenceFactory.getRandomOrderedGenome(genomeLength)
        );

        GrassLayer grassLayer = new GrassLayer(
            new GrassFactory(energyOfGrass),
            initialGrassCount,
            grassGrownEachIteration,
            hasEquator
        );
        grassLayer.setNext(animalLayer);

        BoundaryLayer boundaryLayer = new BoundaryLayer(boundary, hasWrappingWorld);
        boundaryLayer.setNext(grassLayer);

        if (initialHoleCount > 0) {
            HoleLayer holeLayer = new HoleLayer(boundary, initialGrassCount);
            holeLayer.setNext(boundaryLayer);

            return holeLayer;
        }

        return boundaryLayer;
    }
}
