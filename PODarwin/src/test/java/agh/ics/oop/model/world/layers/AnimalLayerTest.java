package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.classes.factory.AnimalFactory;
import agh.ics.oop.model.classes.factory.GenomeSequenceFactory;
import agh.ics.oop.model.world.phases.EatPhase;
import agh.ics.oop.model.world.phases.InitPhase;
import agh.ics.oop.model.world.phases.MovePhase;
import agh.ics.oop.model.world.phases.ReproducePhase;
import agh.ics.oop.service.ReproduceAnimalsService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnimalLayerTest {
    @Test
    void shouldNotSpawnOnHoles() {
        HashMap<Vector2D, Vector2D> holes = new HashMap<>();
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(4, 5);
        Vector2D v3 = new Vector2D(5, 6);
        Vector2D v4 = new Vector2D(7, 8);
        holes.put(v1, v2);
        holes.put(v2, v1);
        holes.put(v3, v4);
        holes.put(v4, v3);
        GenomeSequenceFactory genomeSequenceFactory = new GenomeSequenceFactory();
        AnimalLayer animalLayer = new AnimalLayer(
            new AnimalFactory(10),
            mock(ReproduceAnimalsService.class),
            10,
            () -> genomeSequenceFactory.getRandomOrderedGenome(5)
        );
        InitPhase initPhase = new InitPhase();
        initPhase.setHoles(holes);
        initPhase.setMapBoundary(new Boundary(new Vector2D(0, 0), new Vector2D(10, 10)));
        animalLayer.handle(initPhase);
        for (Animal animal : animalLayer.getAnimals()) {
            assertFalse(holes.containsKey(animal.getPosition()));
        }
    }

    @Test
    void shouldChangeEnergy() {
        GenomeSequenceFactory genomeSequenceFactory = new GenomeSequenceFactory();
        HashMap<Animal, Vector2D> animalMoves = new HashMap<>();
        HashSet<Animal> animals = new HashSet<>();
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(4, 5);
        Vector2D v3 = new Vector2D(5, 6);
        Vector2D v4 = new Vector2D(7, 8);
        Animal animal1 = new Animal(v1, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal2 = new Animal(v2, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal3 = new Animal(v3, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal4 = new Animal(v4, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);
        animals.add(animal4);
        animalMoves.put(animal1, v4);
        animalMoves.put(animal2, v3);
        animalMoves.put(animal3, v2);
        animalMoves.put(animal4, v1);
        MovePhase movePhase = new MovePhase();
        movePhase.setNewAnimalMoves(animalMoves);
        AnimalLayer animalLayer = new AnimalLayer(
            new AnimalFactory(10),
            mock(ReproduceAnimalsService.class),
            10,
            () -> genomeSequenceFactory.getRandomOrderedGenome(5)
        );
        animalLayer.setAnimals(animals);
        animalLayer.handle(movePhase);
        for (Animal animal : animalLayer.getAnimals()) {
            assertTrue(animal.getEnergy() == 9);
            assertEquals(1,animal.getAnimalStats().getAge());
        }
    }

    @Test
    void shouldAddEnergy() {
        GenomeSequenceFactory genomeSequenceFactory = new GenomeSequenceFactory();
        HashSet<Animal> animals = new HashSet<>();
        HashSet<Grass> grass = new HashSet<>();
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(4, 5);
        Vector2D v3 = new Vector2D(5, 6);
        Grass g1 = new Grass(v3, 5);
        Animal animal1 = new Animal(v1, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal2 = new Animal(v2, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal3 = new Animal(v3, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal4 = new Animal(v3, genomeSequenceFactory.getRandomOrderedGenome(5), 12);
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);
        animals.add(animal4);
        grass.add(g1);
        EatPhase eatPhase = new EatPhase();
        eatPhase.setGrass(grass);
        AnimalLayer animalLayer = new AnimalLayer(
            new AnimalFactory(10),
            mock(ReproduceAnimalsService.class),
            0,
            () -> genomeSequenceFactory.getRandomOrderedGenome(5)
        );
        animalLayer.setAnimals(animals);
        animalLayer.handle(eatPhase);
        assertEquals(17, animal4.getEnergy());
        assertEquals(10, animal3.getEnergy());
        assertEquals(10, animal2.getEnergy());
        assertEquals(10, animal1.getEnergy());
        assertEquals(0, eatPhase.getGrass().size());
        assertEquals(1,animal4.getAnimalStats().getPlantsConsumed());
    }
    @Test
    void shouldAddAnimals(){
        ReproductionParams reproductionParams = new ReproductionParams(10,1,0,0);
        GenomeSequenceFactory genomeSequenceFactory = new GenomeSequenceFactory();
        HashSet<Animal> animals = new HashSet<>();
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(4, 5);
        Vector2D v3 = new Vector2D(5, 6);
        Vector2D v4 = new Vector2D(5, 6);
        Animal animal1 = new Animal(v1, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal2 = new Animal(v2, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal3 = new Animal(v3, genomeSequenceFactory.getRandomOrderedGenome(5), 10);
        Animal animal4 = new Animal(v4, genomeSequenceFactory.getRandomOrderedGenome(5), 12);
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);
        animals.add(animal4);
        ReproducePhase reproducePhase = new ReproducePhase();
        AnimalLayer animalLayer = new AnimalLayer(
            new AnimalFactory(10),
            new ReproduceAnimalsService(reproductionParams),
            10,
            () -> genomeSequenceFactory.getRandomOrderedGenome(5)
        );
        animalLayer.setAnimals(animals);
        animalLayer.handle(reproducePhase);
        assertEquals(11,animal4.getEnergy());
        assertEquals(9,animal3.getEnergy());
        assertEquals(1,animal4.getAnimalStats().getNumOfDescendants());
        assertEquals(1,animal3.getAnimalStats().getNumOfDescendants());
        assertEquals(1,animal4.getAnimalStats().getNumOfChildren());
        assertEquals(1,animal3.getAnimalStats().getNumOfChildren());
        for(Animal animal: animalLayer.getAnimals()){
            if(animal.getAnimalStats().getParents().contains(animal3)){
                assertEquals(2,animal.getEnergy());
            }
        }
    }

}
