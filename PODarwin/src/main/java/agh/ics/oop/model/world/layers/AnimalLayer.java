package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.classes.factory.AnimalFactory;
import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.model.world.phases.*;
import agh.ics.oop.utils.*;

import java.util.*;
import java.util.function.Supplier;

public class AnimalLayer implements MapLayer {
    private final AnimalFactory animalFactory;
    private final ReproductionParams reproductionParams;
    private final Supplier<GenomeSequence> genomeSequenceSupplier;
    private final int initialAnimalsCount;
    private HashSet<Animal> animals;

    public AnimalLayer(
        AnimalFactory animalFactory,
        ReproductionParams reproductionParams,
        int initialAnimalsCount,
        Supplier<GenomeSequence> genomeSequenceSupplier
    ) {
        this.animalFactory = animalFactory;
        this.reproductionParams = reproductionParams;
        this.genomeSequenceSupplier = genomeSequenceSupplier;
        this.initialAnimalsCount = initialAnimalsCount;
    }

    @Override
    public boolean handle(InitPhase phase) {
        HashSet<Animal> animals = new HashSet<>();
        //Animals cant spawn on holes?
        PositionsRange positionsRange = new PositionsRange(phase.getMapBoundary(),
            this.initialAnimalsCount, new HashSet<>(phase.getHoles().keySet()));
        for (Vector2D position : positionsRange) {
            animals.add(this.animalFactory.getAnimal(position, genomeSequenceSupplier.get()));
        }
        phase.setAnimals(animals);
        this.animals = animals;
        return true;
    }

    @Override
    public boolean handle(MovePhase phase) {
        MoveAnimalService moveAnimalService = new MoveAnimalService(animals, phase.getNewAnimalMoves());
        phase.setNewAnimalMoves(moveAnimalService.moveAnimals());
        return true;
    }


    @Override
    public boolean handle(EatPhase phase) {
        EatAnimalService eatAnimalService = new EatAnimalService(phase.getGrassPosition(), this.animals);
        HashSet<Grass> currentGrass = phase.getGrass();
        currentGrass.removeAll(eatAnimalService.eatGrass());
        phase.setGrass(currentGrass);
        return true;
    }

    @Override
    public boolean handle(ReproducePhase phase) {
        ReproduceAnimalService reproduceAnimalService = new ReproduceAnimalService(this.animals, this.reproductionParams);
        reproduceAnimalService.reproduceAnimals();
        return true;
    }

    @Override
    public boolean handle(CleanupPhase phase) {
        phase.getRemovedAnimals().forEach(this.animals::remove);
        return true;
    }

    public HashSet<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(HashSet<Animal> animals) {
        this.animals = animals;
    }
}
