package agh.ics.oop.model.world.layers;

import agh.ics.oop.model.world.phases.SummaryPhase;

import java.io.File;

public class FileLayer extends AbstractLayer {
    File statsFile;

    public FileLayer(File statsFile) {
        this.statsFile = statsFile;
    }

    public File getStatsFile() {
        return statsFile;
    }

    public void setStatsFile(File statsFile) {
        this.statsFile = statsFile;
    }

    @Override
    public void handle(SummaryPhase phase) {
        phase.setStatsFile(statsFile);
    }
}
