package agh.ics.oop.model.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalStatsTest {
    @Test
    void shouldChangeStats(){
        AnimalStats animalStats = new AnimalStats();
        animalStats.increasePlantsConsumed();
        animalStats.increasePlantsConsumed();
        animalStats.increaseAge();
        animalStats.increaseChildren();
        animalStats.increaseChildren();
        animalStats.increaseDescendants();
        animalStats.setDead(5);
        assertEquals(2,animalStats.getPlantsConsumed());
        assertEquals(1,animalStats.getAge());
        assertEquals(2,animalStats.getNumOfChildren());
        assertEquals(1,animalStats.getNumOfDescendants());
        assertEquals(5,animalStats.getDeathDay());
    }

}
