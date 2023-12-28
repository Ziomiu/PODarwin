package agh.ics.oop.model.classes;

import agh.ics.oop.model.enums.Genome;
import agh.ics.oop.utils.LoopingIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class AlternatingGenomeSequence implements GenomeSequence {
    private final List<Genome> genomes;
    private final LoopingIterator<Genome> genomesIterator;

    public AlternatingGenomeSequence(List<Genome> genomes) {
        this.genomes = genomes;
        var reversedGenome = new ArrayList<>(genomes);
        Collections.reverse(reversedGenome);
        this.genomesIterator = new LoopingIterator<>(
            Stream.concat(genomes.stream(), reversedGenome.stream()).toList()
        );
    }

    @Override
    public Genome nextInSequence() {
        return genomesIterator.next();
    }

    @Override
    public List<Genome> getAllGenomes() {
        return genomes;
    }
}
