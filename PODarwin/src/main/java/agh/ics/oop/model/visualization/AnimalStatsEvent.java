package agh.ics.oop.model.visualization;

import java.util.HashMap;
import java.util.Map;

public record AnimalStatsEvent(
    String fullGenome,
    String currentGenome,
    int currentEnergy,
    int eatenGrass,
    int childCount,
    int descendantsCount,
    int lifetime,
    int deathDay
) {
    public Map<String, String> asMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Full Genome", fullGenome);
        map.put("Current Genome", currentGenome);
        map.put("Current Energy", String.valueOf(currentEnergy));
        map.put("Eaten Grass", String.valueOf(eatenGrass));
        map.put("Child Count", String.valueOf(childCount));
        map.put("Descendants Count", String.valueOf(descendantsCount));
        map.put("Lifetime", String.valueOf(lifetime));
        map.put("Death Day", deathDay == -1 ? "-" : String.valueOf(deathDay));
        return map;
    }
}
