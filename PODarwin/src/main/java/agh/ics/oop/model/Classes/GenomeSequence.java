package agh.ics.oop.model.Classes;

import agh.ics.oop.model.EnumClasses.Genome;

import java.util.List;

public interface GenomeSequence {
    Genome nextInSequence();

    List<Genome> getAllGenomes();
}
