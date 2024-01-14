package agh.ics.oop.model.world.phases;

import agh.ics.oop.model.classes.*;
import agh.ics.oop.model.visualization.GlobalStatsEvent;
import agh.ics.oop.model.world.layers.MapLayer;
import agh.ics.oop.utils.CsvWriter;
import agh.ics.oop.utils.StatsGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class SummaryPhase implements Phase {
    private Boundary mapBoundary = null;
    private Boundary preferredGrassFields = null;
    private File statsFile = null;
    private Set<Animal> animals = Set.of();
    private Set<Grass> grass = Set.of();
    private Set<Hole> tunnels = Set.of();
    private Set<Animal> deadAnimals = Set.of();
    private StatsGenerator statsGenerator = new StatsGenerator(this);
    private int day;

    @Override
    public void accept(MapLayer layer) {
        layer.handle(this);
    }

    public void setMapBoundary(Boundary boundary) {
        mapBoundary = boundary;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public Set<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public void setDeadAnimals(Set<Animal> deadAnimals) {
        for (Animal animal : deadAnimals) {
            if (animal.getAnimalStats().getDeathDay() == -1) {
                animal.getAnimalStats().setDead(day);
            }
        }
        this.deadAnimals = deadAnimals;
    }

    public Set<Grass> getGrass() {
        return grass;
    }

    public void setGrass(Set<Grass> grass) {
        this.grass = grass;
    }

    public Set<Hole> getTunnels() {
        return tunnels;
    }

    public void setTunnels(Set<Hole> tunnels) {
        this.tunnels = tunnels;
    }

    public Boundary getMapBoundary() {
        return mapBoundary;
    }

    public File getStatsFile() {
        return statsFile;
    }

    public void setStatsFile(File statsFile) {
        this.statsFile = statsFile;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Boundary getPreferredGrassFields() {
        return preferredGrassFields;
    }

    public void setPreferredGrassFields(Boundary preferredGrassFields) {
        this.preferredGrassFields = preferredGrassFields;
    }

    public GlobalStatsEvent getGlobalStats() {
        GlobalStatsEvent globalStatsEvent = statsGenerator.generateStats();
        if (statsFile != null) {
            writeStats(statsGenerator.getStringStats());
        }
        return globalStatsEvent;
    }

    private void writeStats(ArrayList<String> stats) {
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeStatsToFile(stats, statsFile);
    }
}
