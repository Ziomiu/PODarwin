package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.service.AnimalFactory;
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
    private HashSet<Animal> deadAnimals = new HashSet<>();
    private HashMap<Animal, Vector2D> newbornMoves = new HashMap<>();

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
        HashMap<Animal, Vector2D> tempMoves = phase.getNewAnimalMoves();
        tempMoves.putAll(newbornMoves);
        phase.setNewAnimalMoves(MoveAnimalService.moveAnimals(animals, tempMoves));
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
        newbornMoves.clear();
        newbornMoves.putAll(reproduceAnimalsService.reproduceAnimals(animals));
    }

    @Override
    public void handle(CleanupPhase phase) {
        ArrayList<Animal> removedAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.getEnergy() == 0) {
                removedAnimals.add(animal);
                deadAnimals.add(animal);
                System.out.println("Animal died on position " + animal.getPosition());
            }
        }
        animals.removeAll(removedAnimals);
        phase.setRemovedAnimals(removedAnimals);
        phase.setEatenGrass(eatenGrass);
    }

    @Override
    public void handle(SummaryPhase phase) {
        phase.setAnimals(animals);
        phase.setDeadAnimals(deadAnimals);
    }

    public HashSet<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(HashSet<Animal> animals) {
        this.animals = animals;
    }
}
