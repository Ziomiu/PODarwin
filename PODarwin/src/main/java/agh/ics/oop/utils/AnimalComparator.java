package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Animal;

import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        if (o1.getEnergy() != o2.getEnergy()) {
            return o1.getEnergy() - o2.getEnergy();
        }

        int age1 = o1.getAnimalStats().getAge();
        int age2 = o2.getAnimalStats().getAge();
        if (age1 != age2) {
            return age1 - age2;
        }

        int childrenCount1 = o1.getAnimalStats().getNumOfChildren();
        int childrenCount2 = o2.getAnimalStats().getNumOfChildren();
        if (childrenCount1 != childrenCount2) {
            return childrenCount1 - childrenCount2;
        }

        return (new Random()).nextInt(2) - 1;
    }
}
