package agh.ics.oop.service;

import agh.ics.oop.model.classes.AlternatingGenomeSequence;
import agh.ics.oop.model.classes.OrderedGenomeSequence;
import agh.ics.oop.model.enums.Genome;

import java.util.List;
import java.util.Random;

public class GenomeSequenceFactory {
    public OrderedGenomeSequence getRandomOrderedGenome(int genomeLength) {
        return new OrderedGenomeSequence(generateRandomGenomes(genomeLength));
    }

    public AlternatingGenomeSequence getRandomAlternatingGenome(int genomeLength) {
        return new AlternatingGenomeSequence(generateRandomGenomes(genomeLength));
    }

    private List<Genome> generateRandomGenomes(int genomeLength) {
        Random random = new Random();
        var enumValues = Genome.values();
        return random.ints(genomeLength, 0, Genome.values().length)
                   .mapToObj(i -> enumValues[i]).toList();
    }
}
