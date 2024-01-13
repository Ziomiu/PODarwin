package agh.ics.oop.service;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.utils.AnimalComparator;
import agh.ics.oop.utils.NumbersRange;

import java.util.*;

public class ReproduceAnimalsService {
    private final ReproductionParams reproductionParams;
    private final AnimalFactory animalFactory;

    public ReproduceAnimalsService(
        AnimalFactory animalFactory,
        ReproductionParams reproductionParams
    ) {
        this.animalFactory = animalFactory;
        this.reproductionParams = reproductionParams;
    }

    private HashMap<Vector2D, ArrayList<Animal>> groupAnimalsReproduce(HashSet<Animal> animals) {
        HashMap<Vector2D, ArrayList<Animal>> groupedAnimals = new HashMap<>();
        for (Animal animal : animals) {
            if (animal.getEnergy() >= reproductionParams.reproductionEnergyThreshold()) {
                Vector2D position = animal.getPosition();
                groupedAnimals.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
            }
        }
        return groupedAnimals;
    }

    public HashMap<Animal, Vector2D> reproduceAnimals(HashSet<Animal> animals) {
        HashMap<Animal, Vector2D> childrenMoves = new HashMap<>();
        HashMap<Vector2D, ArrayList<Animal>> groupedAnimals = groupAnimalsReproduce(animals);
        AnimalComparator animalComparator = new AnimalComparator();
        for (Vector2D position : groupedAnimals.keySet()) {
            if (groupedAnimals.get(position).size() >= 2) {
                groupedAnimals.get(position).sort(animalComparator);
                childrenMoves.put(addChild(animals, groupedAnimals.get(position).get(groupedAnimals.get(position).size() - 1),
                    groupedAnimals.get(position).get(groupedAnimals.get(position).size() - 2)), position);
            }
        }
        return childrenMoves;
    }

    private Animal addChild(HashSet<Animal> animals, Animal parent1, Animal parent2) {
        Random random = new Random();
        boolean isGenomeOrdered = parent1.getGenomeSequence() instanceof OrderedGenomeSequence;
        int genesPart = random.nextInt(2);
        int genesLen = parent1.getGenes().size();
        int numOfGenesFromBiggerParent = (int) Math.floor(((double) parent1.getEnergy() / (parent1.getEnergy()
            + parent2.getEnergy())) * genesLen);
        int numOfGensFromSmallerParent = genesLen - numOfGenesFromBiggerParent;
        parent1.removeEnergy(reproductionParams.reproductionEnergyRequired());
        parent2.removeEnergy(reproductionParams.reproductionEnergyRequired());
        List<Genome> newGenome = new ArrayList<>();
        // Animal gets left part of genes from bigger parent
        if (genesPart == 1) {
            for (int i = 0; i < numOfGenesFromBiggerParent; i++) {
                newGenome.add(parent1.getGenes().get(i));
            }
            for (int i = numOfGenesFromBiggerParent; i < genesLen; i++) {
                newGenome.add(parent2.getGenes().get(i));
            }
        } else {
            // Animal gets left part of genes from smaller parent
            for (int i = 0; i < numOfGensFromSmallerParent; i++) {
                newGenome.add(parent1.getGenes().get(i));
            }
            for (int i = numOfGensFromSmallerParent; i < genesLen; i++) {
                newGenome.add(parent2.getGenes().get(i));
            }
        }
        mutate(newGenome);
        Animal child = animalFactory.getAnimal(
            parent1.getPosition(),
            isGenomeOrdered ? new OrderedGenomeSequence(newGenome) : new AlternatingGenomeSequence(newGenome),
            2 * reproductionParams.reproductionEnergyRequired(),
            parent1,
            parent2
        );
        animals.add(child);
        System.out.println("Animal was born on position " + child.getPosition());
        return child;
    }

    private void mutate(List<Genome> newGenome) {
        Random random = new Random();
        int numOfMutations = random.nextInt(reproductionParams.reproductionMutationMax()
            - reproductionParams.reproductionMutationMin() + 1) + reproductionParams.reproductionMutationMin();
        List<Integer> indexes = new NumbersRange(numOfMutations, newGenome.size()).getNumbers();
        for (int index : indexes) {
            newGenome.set(index, Genome.values()[random.nextInt(Genome.values().length)]);
        }
    }
}
