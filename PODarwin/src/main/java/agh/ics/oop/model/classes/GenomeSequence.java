package agh.ics.oop.model.classes;

import agh.ics.oop.model.enums.Genome;

import java.util.List;

public interface GenomeSequence {
    Genome nextInSequence();

    List<Genome> getAllGenomes();
}
