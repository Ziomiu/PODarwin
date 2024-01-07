package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.Grass;
import agh.ics.oop.model.classes.Vector2D;
import agh.ics.oop.model.world.layers.MapLayer;

import java.util.HashMap;
import java.util.HashSet;

public class EatPhase implements Phase {
    private HashSet<Grass> grass;

    public HashSet<Grass> getGrass() {
        return grass;
    }

    public HashMap<Vector2D, Grass> getGrassPosition() {
        return new HashMap<>(
            this.grass.stream().collect(
                HashMap::new,
                (map, grass1) -> map.put(grass1.getPosition(), grass1),
                HashMap::putAll
            )
        );
    }

    public void setGrass(HashSet<Grass> grass) {
        this.grass = grass;
    }

    @Override
    public void accept(MapLayer layer) {
        System.out.println("EAT");
        layer.handle(this);
    }
}
