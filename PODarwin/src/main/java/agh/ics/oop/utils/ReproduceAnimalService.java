package agh.ics.oop.utils;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.enums.Genome;

import java.util.*;

public class ReproduceAnimalService {
    private HashSet<Animal> animals;
    private ReproductionParams reproductionParams;

    public ReproduceAnimalService(HashSet<Animal> animals, ReproductionParams reproductionParams) {
        this.animals = animals;
        this.reproductionParams = reproductionParams;
    }

    private HashMap<Vector2D, ArrayList<Animal>> groupAnimalsReproduce() {
        HashMap<Vector2D, ArrayList<Animal>> groupedAnimals = new HashMap<>();
        for (Animal animal : animals) {
            if (animal.getEnergy() >= reproductionParams.reproductionEnergyThreshold()) {
                Vector2D position = animal.getPosition();
                groupedAnimals.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
            }
        }
        return groupedAnimals;
    }

    public void reproduceAnimals() {
        HashMap<Vector2D, ArrayList<Animal>> groupedAnimals = groupAnimalsReproduce();
        AnimalComparator animalComparator = new AnimalComparator();
        for (Vector2D position : groupedAnimals.keySet()) {
            if (groupedAnimals.get(position).size() >= 2) {
                Collections.sort(groupedAnimals.get(position), animalComparator);
                addChild(groupedAnimals.get(position).get(groupedAnimals.get(position).size() - 1),
                    groupedAnimals.get(position).get(groupedAnimals.get(position).size() - 2));
            }
        }
    }

    private void addChild(Animal parent1, Animal parent2) {
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
        //Animal gets left part of genes from bigger parent
        if (genesPart == 1) {
            for (int i = 0; i < numOfGenesFromBiggerParent; i++) {
                newGenome.add(parent1.getGenes().get(i));
            }
            for (int i = numOfGenesFromBiggerParent; i < genesLen; i++) {
                newGenome.add(parent2.getGenes().get(i));
            }
        } else {
            ////Animal gets left part of genes from smaller parent
            for (int i = 0; i < numOfGensFromSmallerParent; i++) {
                newGenome.add(parent1.getGenes().get(i));
            }
            for (int i = numOfGensFromSmallerParent; i < genesLen; i++) {
                newGenome.add(parent2.getGenes().get(i));
            }
        }
        mutate(newGenome);
        Animal child;
        if (isGenomeOrdered) {
            child = new Animal(parent1.getPosition(), new OrderedGenomeSequence(newGenome),
                2 * this.reproductionParams.reproductionEnergyRequired());

        } else {
            child = new Animal(parent1.getPosition(), new AlternatingGenomeSequence(newGenome),
                2 * this.reproductionParams.reproductionEnergyRequired());
        }
        this.animals.add(child);
        child.getAnimalStats().getParents().add(parent1);
        child.getAnimalStats().getParents().add(parent2);
        parent1.getAnimalStats().addDescendant();
        parent2.getAnimalStats().addDescendant();
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
