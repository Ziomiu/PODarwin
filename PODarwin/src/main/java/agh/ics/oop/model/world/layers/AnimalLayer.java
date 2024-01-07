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

public class AnimalLayer extends AbstractLayer {
    private final AnimalFactory animalFactory;
    private final ReproduceAnimalsService reproduceAnimalsService;
    private final Supplier<GenomeSequence> genomeSequenceSupplier;
    private final int initialAnimalsCount;
    private HashSet<Grass> eatenGrass;
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
        this.eatenGrass = new HashSet<>();
        this.animals = new HashSet<>();
    }

    @Override
    public void handle(InitPhase phase) {
        //Animals cant spawn on holes?
        PositionsRange positionsRange = new PositionsRange(
            phase.getMapBoundary(),
            this.initialAnimalsCount, new HashSet<>(phase.getHoles().keySet())
        );
        for (Vector2D position : positionsRange) {
            animals.add(animalFactory.getAnimal(position, genomeSequenceSupplier.get()));
        }
        phase.setAnimals(animals);
    }

    @Override
    public void handle(MovePhase phase) {
        phase.setNewAnimalMoves(MoveAnimalService.moveAnimals(animals, phase.getNewAnimalMoves()));
    }


    @Override
    public void handle(EatPhase phase) {
        HashSet<Grass> currentGrass = phase.getGrass();
        eatenGrass = FeedAnimalService.eatGrass(phase.getGrassPosition(), animals);
        currentGrass.removeAll(eatenGrass);
        phase.setGrass(currentGrass);
    }

    @Override
    public void handle(ReproducePhase phase) {
        reproduceAnimalsService.reproduceAnimals(animals);
    }

    @Override
    public void handle(CleanupPhase phase) {
        phase.getRemovedAnimals().forEach(animals::remove);
        phase.setEatenGrass(eatenGrass);
    }

    public HashSet<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(HashSet<Animal> animals) {
        this.animals = animals;
    }
}
