package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Animal;

import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        if (o1.getEnergy() > o2.getEnergy()) {
            return 1;
        } else if (o1.getEnergy() == o2.getEnergy()) {
            if (o1.getAnimalStats().getAge() > o2.getAnimalStats().getAge()) {
                return 1;
            } else if (o1.getAnimalStats().getAge() == o2.getAnimalStats().getAge()) {
                if (o1.getAnimalStats().getNumOfChildren() > o2.getAnimalStats().getNumOfChildren()) {
                    return 1;
                } else if (o1.getAnimalStats().getNumOfChildren() == o2.getAnimalStats().getNumOfChildren()) {
                    Random random = new Random();
                    return random.nextInt(2) == 0 ? -1 : 1;
                }
                return -1;
            }
            return -1;
        }
        return -1;
    }
}
