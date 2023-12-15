package agh.ics.oop.model.Classes;

import agh.ics.oop.model.EnumClasses.Genom;

public class Animal implements WorldElement, Drawable {
    private Vector2D position;
    private int energy;
    private GenomSequence genom;

    public void setGenom(GenomSequence genom) {
        this.genom = genom;
    }


    @Override
    public void draw() {

    }

    @Override
    public Vector2D getPosition() {
        return null;
    }

    public Genom nextGenom() {
        return null;
    }

    public Genom prevGenom() {
        return null;
    }
}
