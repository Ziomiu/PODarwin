package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.classes.factory.AnimalFactory;
import agh.ics.oop.model.world.phases.*;
import agh.ics.oop.service.FeedAnimalService;
import agh.ics.oop.service.MoveAnimalService;
import agh.ics.oop.service.ReproduceAnimalsService;
import agh.ics.oop.utils.*;

import java.util.*;
import java.util.function.Supplier;

public class AnimalLayer implements MapLayer {
    private final AnimalFactory animalFactory;
    private final ReproduceAnimalsService reproduceAnimalsService;
    private final Supplier<GenomeSequence> genomeSequenceSupplier;
    private final int initialAnimalsCount;
    private HashSet<Animal> animals;

    public AnimalLayer(
        AnimalFactory animalFactory,
        ReproduceAnimalsService reproduceAnimalsService,
        int initialAnimalsCount,
        Supplier<GenomeSequence> genomeSequenceSupplier
    ) {
        this.animalFactory = animalFactory;
        this.reproduceAnimalsService = reproduceAnimalsService;
        this.genomeSequenceSupplier = genomeSequenceSupplier;
        this.initialAnimalsCount = initialAnimalsCount;
        this.animals = new HashSet<>();
    }

    @Override
    public boolean handle(InitPhase phase) {
        //Animals cant spawn on holes?
        PositionsRange positionsRange = new PositionsRange(
            phase.getMapBoundary(),
            this.initialAnimalsCount, new HashSet<>(phase.getHoles().keySet())
        );
        for (Vector2D position : positionsRange) {
            animals.add(animalFactory.getAnimal(position, genomeSequenceSupplier.get()));
        }
        phase.setAnimals(animals);
        return true;
    }

    @Override
    public boolean handle(MovePhase phase) {
        phase.setNewAnimalMoves(MoveAnimalService.moveAnimals(animals, phase.getNewAnimalMoves()));
        return true;
    }


    @Override
    public boolean handle(EatPhase phase) {
        HashSet<Grass> currentGrass = phase.getGrass();
        currentGrass.removeAll(FeedAnimalService.eatGrass(phase.getGrassPosition(), animals));
        phase.setGrass(currentGrass);
        return true;
    }

    @Override
    public boolean handle(ReproducePhase phase) {
        reproduceAnimalsService.reproduceAnimals(animals);
        return true;
    }

    @Override
    public boolean handle(CleanupPhase phase) {
        phase.getRemovedAnimals().forEach(animals::remove);
        return true;
    }

    public HashSet<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(HashSet<Animal> animals) {
        this.animals = animals;
    }
}
