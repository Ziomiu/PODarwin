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
        HashMap<Vector2D, Grass> grassPositions = new HashMap<>(this.grass.stream()
            .collect(HashMap::new,
                (map, grass) -> map.put(grass.getPosition(), grass),
                HashMap::putAll));
        return grassPositions;
    }

    public void setGrass(HashSet<Grass> grass) {
        this.grass = grass;
    }

    @Override
    public void accept(MapLayer layer) {

    }
}
