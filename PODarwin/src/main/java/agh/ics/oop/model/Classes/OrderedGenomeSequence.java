package agh.ics.oop.model.Classes;

import agh.ics.oop.model.EnumClasses.Genome;
import agh.ics.oop.utils.LoopingIterator;
import java.util.List;

public class OrderedGenomeSequence implements GenomeSequence {
    private final List<Genome> genomes;
    private final LoopingIterator<Genome> genomesIterator;

    public OrderedGenomeSequence(List<Genome> genomes) {
        this.genomes = genomes;
        genomesIterator = new LoopingIterator<>(genomes);
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
