package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.ReproductionParams;
import agh.ics.oop.model.classes.factory.AnimalFactory;
import agh.ics.oop.model.classes.GenomeSequence;

import java.util.function.Supplier;

public class AnimalLayer implements MapLayer {
    private final AnimalFactory animalFactory;
    private final ReproductionParams reproductionParams;
    private final Supplier<GenomeSequence> genomeSequenceSupplier;

    public AnimalLayer(
        AnimalFactory animalFactory,
        ReproductionParams reproductionParams,
        Supplier<GenomeSequence> genomeSequenceSupplier
    ) {
        this.animalFactory = animalFactory;
        this.reproductionParams = reproductionParams;
        this.genomeSequenceSupplier = genomeSequenceSupplier;
    }
}
